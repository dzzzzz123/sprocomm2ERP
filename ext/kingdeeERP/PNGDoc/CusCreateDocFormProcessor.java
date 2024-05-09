package ext.kingdeeERP.PNGDoc;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor;
import ext.kingdeeERP.PNGDoc.service.CusDocService;
import wt.doc.WTDocument;
import wt.util.WTException;

import java.util.List;

/**
 * @author lvtian
 * @date 2024/5/6 17:05
 */
public class CusCreateDocFormProcessor extends CreateDocFormProcessor {

    @Override
    public FormResult doOperation(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        return super.doOperation(nmCommandBean, list);
    }

    @Override
    public FormResult preProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        return super.preProcess(nmCommandBean, list);
    }

    @Override
    public FormResult postProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        return super.postProcess(nmCommandBean, list);
    }

    @Override
    public FormResult postTransactionProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        System.out.println("--------postTransactionProcess------");
        WTDocument doc = (!list.isEmpty())
                ? (list.get(0).getObject() instanceof WTDocument ? (WTDocument) list.get(0).getObject() : null)
                : null;
        String process = CusDocService.process(doc);
        // 如果返回的不为空说明图片名没有对应的部件
        if (process != null) {
            throw new WTException("无法通过图片名称获取到" + process + "编号的部件，请检查图片文件名是否符合规范");
        }
        return super.postTransactionProcess(nmCommandBean, list);
    }
}
