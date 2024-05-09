package ext.kingdeeERP.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import wt.change2.*;
import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.maturity.MaturityHelper;
import wt.maturity.PromotionNotice;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.workflow.engine.WfProcess;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowUtil {

    static Logger logger = LogManager.getLogger(WorkFlowUtil.class);

    /**
     * 将传入的PBO解析为方便处理的List<T>
     *
     * @param obj        PBO对象
     * @param targetType 想解析得到的列表包含对象
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> getListFromPBO(WTObject obj, Class<T> targetType) {
        List<T> list = new ArrayList<>();
        try {
            if (targetType.isInstance(obj)) {
                list.add(targetType.cast(obj));
            } else if (obj instanceof PromotionNotice) {
                PromotionNotice pn = (PromotionNotice) obj;
                QueryResult qr = MaturityHelper.service.getPromotionTargets(pn);
                list = CommonUtil.getListFromQR(qr, targetType);
            } else if (obj instanceof WTChangeOrder2) {
                WTChangeOrder2 co = (WTChangeOrder2) obj;
                QueryResult qr = ChangeHelper2.service.getChangeablesAfter(co);
                list = CommonUtil.getListFromQR(qr, targetType);
            } else if (obj instanceof WTChangeActivity2) {
                WTChangeActivity2 eca = (WTChangeActivity2) obj;
                QueryResult qr = ChangeHelper2.service.getChangeablesAfter(eca);
                list = CommonUtil.getListFromQR(qr, targetType);
            } else if (obj instanceof WTChangeRequest2) {
                WTChangeRequest2 ecr = (WTChangeRequest2) obj;
                QueryResult qr = ChangeHelper2.service.getChangeables(ecr);
                list = CommonUtil.getListFromQR(qr, targetType);
            } else {
                logger.error("将PBO转换为其他持久化对象时出错: 数据不正确！");
            }
        } catch (WTException e) {
            logger.error("将PBO转换为其他持久化对象时出错", e);
        }
        return list;
    }

    /**
     * 根据评审对象（PBO）获取WfProcess对象
     *
     * @param obj PBO 对象
     * @return {@link WfProcess }
     */
    @SuppressWarnings("deprecation")
    public static WfProcess getProcessByPbo(WTObject obj) {
        WfProcess process = null;
        try {
            String pboId = "";
            if (obj instanceof VersionableChangeItem) {
                pboId = "VR:" + obj.getClass().getName() + ":"
                        + ((VersionableChangeItem) obj).getIterationInfo().getBranchId();
                ;
            } else if (obj instanceof RevisionControlled) {
                pboId = "VR:" + obj.getClass().getName() + ":"
                        + ((RevisionControlled) obj).getIterationInfo().getBranchId();
            } else if (obj instanceof PromotionNotice) {
                PromotionNotice pn = (PromotionNotice) obj;
                pboId = "OR:" + obj.getClass().getName() + ":" + pn.getPersistInfo().getObjectIdentifier().getId();
            } else {
                return null;
            }
            QuerySpec qs = new QuerySpec(WfProcess.class);
            qs.appendWhere(new SearchCondition(WfProcess.class, WfProcess.BUSINESS_OBJ_REFERENCE, SearchCondition.EQUAL,
                    pboId));
            QueryResult qr = PersistenceHelper.manager.find(qs);
            while (qr != null && qr.hasMoreElements()) {
                WfProcess tmpProcess = (WfProcess) qr.nextElement();
                if (process == null) {
                    process = tmpProcess;
                } else {
                    if (tmpProcess.getStartTime().after(process.getStartTime())) {
                        process = tmpProcess;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("从PBO获取WFProcess对象时出错", e);
        }
        return process;
    }
}
