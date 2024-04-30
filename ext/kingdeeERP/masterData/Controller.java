package ext.kingdeeERP.masterData;

import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import ext.kingdeeERP.util.PersistenceUtil;
import org.apache.log4j.Logger;
import wt.fc.WTObject;
import wt.part.WTPart;

import java.util.List;
import java.util.Set;

public class Controller {

    static Logger logger = Logger.getLogger(Controller.class);

    /**
     * 发送部件给ERP
     *
     * @param ref 部件对象
     * @return {@link String} 返回错误信息
     */
    public static String sendParts2ERP(WTObject ref) {
        WTPart part = (WTPart) ref;
        // 获取token
        ext.kingdeeERP.token.Service.process();
        // 必须检入状态才能发送
        if (PersistenceUtil.isCheckOut(part)) {
            return part.getNumber() + " 该部件是检出状态!请先检入该部件后操作!";
        }
        // 发送到ERP
        String resultJson = Service.sendSinglePart2ERP(part);
        // 对接口请求结果中的错误信息处理后返回错误信息
        String resultMsg = CommonUtil.processResult(resultJson);
        if (resultMsg != null && resultMsg.equals("IsSuccess")) {
            Config.setPart2ERPStatus(part, "物料已成功发送到ERP");
            Config.setERPErrorMsg(part, "");
            return null;
        } else {
            Config.setPart2ERPStatus(part, "物料发送到ERP失败");
            Config.setERPErrorMsg(part, resultMsg);
            return resultMsg;
        }
    }

    /**
     * 非项目部件，正在工作状态时，修改时检入自动将部件发送到ERP
     *
     * @param part 部件对象
     */
    public static void sendParts2ERPCheckIn(WTPart part) {
        if (part != null && checkPart(part)) {
            // 发送部件到ERP
            String msg = sendParts2ERP(part);
            if (msg != null && !msg.equals("IsSuccess")) {
                logger.error("当部件为非项目部件，且正在工作状态时，发送部件到ERP出错:/n" + msg);
            }
        }
    }

    /**
     * 校验部件是否需要发送到ERP
     *
     * @param part 部件对象
     * @return boolean 是否发送到ERP
     */
    private static boolean checkPart(WTPart part) {
        // 部件子类型为项目部件时不发送
        String partTypeInternalName = PersistenceUtil.getPartTypeInternalName(part);
        String noPermissionInternalName = Config.InternalName();
        if (partTypeInternalName.equals(noPermissionInternalName)) {
            return false;
        }
        // 部件状态为正在工作时发送
        String partState = part.getState().toString();
        Set<String> permissionPartStates = Config.PermissionPartState();
        return permissionPartStates.contains(partState);
    }

    /**
     * 在工作流中将部件等内容发送到ERP
     *
     * @param ref PBO对象
     * @return {@link String}
     */
    public static String sendParts2ERPInProgress(WTObject ref) {
        StringBuilder errMsg = new StringBuilder();
        List<WTPart> list = CommonUtil.getListFromPBO(ref, WTPart.class);
        for (WTPart part : list) {
            // 获取最新部件发送到ERP
            part = (WTPart) PersistenceUtil.getLatestObj(part.getMaster());
            errMsg.append(sendParts2ERP(part)).append("/n");
        }
        return errMsg.toString();
    }
}
