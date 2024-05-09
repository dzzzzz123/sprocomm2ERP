package ext.kingdeeERP.createDoc;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;
import com.ptc.windchill.enterprise.doc.forms.CreateDocFormProcessor;
import ext.kingdeeERP.createDoc.service.CusDocService;
import ext.kingdeeERP.util.WatermarkUtil;
import wt.content.*;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.part.WTPartReferenceLink;
import wt.pom.PersistenceException;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.config.LatestConfigSpec;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static wt.org.WTPrincipalReference.newWTPrincipalReference;

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
        assert doc != null;
        String name = doc.getName();
        String[] split = name.split("-");
        // 如果文件名中没有-抛出一个异常 如果分割后发现数组长度为1抛出异常
        if (split.length <= 1){
            throw new WTException("请检查图片文件名是否符合规范或相关部件是否存在");
        }
        String process = CusDocService.process(doc);
        // 如果返回的不为空说明图片名没有对应的部件
        if(process != null) {
            throw new WTException("无法通过图片名称获取到"+process+"编号的部件，请检查图片文件名是否符合规范");
        }
        return super.postTransactionProcess(nmCommandBean, list);
    }
}
