package ext.kingdeeERP.BOM;

import ext.generic.reviewObject.model.ProcessReviewObjectLink;
import ext.generic.reviewObject.model.ProcessReviewObjectLinkHelper;
import ext.kingdeeERP.BOM.service.Service;
import ext.kingdeeERP.util.PersistenceUtil;
import ext.kingdeeERP.util.WorkFlowUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.part.WTPart;
import wt.util.WTException;
import wt.workflow.engine.WfProcess;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    static Logger logger = LogManager.getLogger(Controller.class);

    /**
     * 发送部件给ERP
     *
     * @param ref 部件对象
     * @return {@link String} 返回错误信息
     */
    public static String sendBOM2ERP(WTObject ref) {
        WTPart part = (WTPart) ref;
        // 获取token
        ext.kingdeeERP.token.Service.process();
        // 必须检入状态才能发送
        if (PersistenceUtil.isCheckOut(part)) {
            return part.getNumber() + " 该BOM的最上层部件是检出状态!请先检入该部件后操作!";
        }
        // 发送到ERP
        return Service.sendSingleBOM2ERP(part);
    }

    /**
     * 在工作流中将BOM发送到ERP
     *
     * @param ref PBO对象
     * @return {@link String}
     */
    public static String sendParts2ERPInProgress(WTObject ref) {
        StringBuilder errMsg = new StringBuilder();
        List<WTPart> list = WorkFlowUtil.getListFromPBO(ref, WTPart.class);
        for (WTPart part : list) {
            String msg = sendBOM2ERP(part);
            if (StringUtils.isNotBlank(msg)) {
                errMsg.append(msg).append("\n");
            }
        }
        list.addAll(getProcessReviewObj(ref));
        return errMsg.toString();
    }

    public static List<WTPart> getProcessReviewObj(WTObject ref) {
        List<WTPart> list = new ArrayList<>();
        try {
            WfProcess wfprocess = WorkFlowUtil.getProcessByPbo(ref);
            if (wfprocess != null) {
                QueryResult queryresult = ProcessReviewObjectLinkHelper.service
                        .getProcessReviewObjectLinkByRoleA(wfprocess);
                while (queryresult != null && queryresult.hasMoreElements()) {
                    ProcessReviewObjectLink link = (ProcessReviewObjectLink) queryresult.nextElement();
                    WTObject obj = (WTObject) link.getRoleBObject();
                    if (obj instanceof WTPart) {
                        list.add((WTPart) obj);
                    }
                }
            }
        } catch (WTException e) {
            logger.error("从PBO对象钟获取部件对象时出错", e);
        }
        return list;
    }
}
