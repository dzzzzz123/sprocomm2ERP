package ext.kingdeeERP.BOM;

import ext.kingdeeERP.BOM.service.Service;
import ext.kingdeeERP.util.CommonUtil;
import ext.kingdeeERP.util.PersistenceUtil;
import wt.fc.WTObject;
import wt.part.WTPart;

import java.util.List;

public class Controller {

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
        String resultJson = Service.sendSingleBOM2ERP(part);
        // 对接口请求结果中的错误信息处理后返回错误信息
        return CommonUtil.processResult(resultJson);
    }

    /**
     * 在工作流中将BOM发送到ERP
     *
     * @param ref PBO对象
     * @return {@link String}
     */
    public static String sendParts2ERPInProgress(WTObject ref) {
        StringBuilder errMsg = new StringBuilder();
        List<WTPart> list = CommonUtil.getListFromPBO(ref, WTPart.class);
        for (WTPart part : list) {
            errMsg.append(sendBOM2ERP(part)).append("/n");
        }
        return errMsg.toString();
    }

}
