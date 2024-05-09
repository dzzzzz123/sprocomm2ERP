package ext.kingdeeERP.PNGDoc;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateMultiDocFormProcessor;
import ext.kingdeeERP.PNGDoc.service.CusDocService;
import wt.doc.WTDocument;
import wt.util.WTException;

import java.util.ArrayList;
import java.util.List;

/**
 * 客制化创建多个文档处理
 *
 * @author lvtian
 * @date 2024/5/7 9:13
 */
public class CusCreateMultiDocFormProcessor extends CreateMultiDocFormProcessor {

    @Override
    public FormResult postTransactionProcess(NmCommandBean nmCommandBean, List<ObjectBean> list) throws WTException {
        System.out.println("--------postTransactionProcess------");
//        WTDocument doc = (!list.isEmpty())
//                ? (list.get(0).getObject() instanceof WTDocument ? (WTDocument) list.get(0).getObject() : null)
//                : null;
        ArrayList<String> partNums = new ArrayList<>();
        if (!list.isEmpty()) {
            for (ObjectBean objectBean : list) {
                Object object = objectBean.getObject();
                if (object instanceof WTDocument) {
                    WTDocument wtDocument = (WTDocument) object;
                    String process = CusDocService.process(wtDocument);
                    if (process != null) {
                        partNums.add(process);
                    }
                }
            }
            if (!partNums.isEmpty()) {
                // 抛出异常
                throw new WTException("无法通过图片名称获取到" + CusDocService.appendStringList(partNums) + "编号的部件，请检查图片文件名是否符合规范");
            }
        }
        return super.postTransactionProcess(nmCommandBean, list);
    }
}
