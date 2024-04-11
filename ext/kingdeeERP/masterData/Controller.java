package ext.kingdeeERP.masterData;

import ext.kingdeeERP.util.PersistenceUtil;
import wt.fc.WTObject;
import wt.part.WTPart;

public class Controller {

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
        return Service.sendSinglePart2ERP(part);
    }

}
