package ext.kingdeeERP.createDoc.service;

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
import wt.util.WTException;
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
import java.util.ArrayList;

import static wt.org.WTPrincipalReference.newWTPrincipalReference;

/**
 * @author lvtian
 * @date 2024/5/7 13:43
 */
public class CusDocService {

    /**
     * 处理新建文档
     * @param doc
     */
    public static String process(WTDocument doc) {
        try {
            File file = new File("H:/temp/");
            System.out.println("doc = " + doc);
            ContentHolder holder = ContentHelper.service.getContents(doc);
            System.out.println("holder = " + holder);
            QueryResult contentsByRole = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
            // 判断是否存在该编号的组件
            String name = doc.getName();
            String[] split = name.split("-");
            // 如果文件名中没有-抛出一个异常 如果分割后发现数组长度为1抛出异常
            if (split.length == 1){
                throw new WTException("请检查图片文件名是否符合规范或相关部件是否存在");
            }
            String partNum =  split[0];
            String partName =  split[1];
            WTPart wtPartByNumber = CusDocService.getWTPartByNumber(partNum);
            if(wtPartByNumber == null) {
                return partNum;
            }
            ArrayList<WTDocument> wtDocuments = new ArrayList<>();
            wtDocuments.add(doc);
            createDocPartLink(wtPartByNumber, wtDocuments, "Describe");
            while (contentsByRole.hasMoreElements()) {
                Object obj = contentsByRole.nextElement();
                System.out.println("obj = " + obj);
                if (obj instanceof ApplicationData) {
                    ApplicationData applicationData = (ApplicationData) obj;
                    System.out.println(applicationData.getFileName());
                    file = new File(file, applicationData.getFileName());
                    // 将图片写入临时目录
                    ContentServerHelper.service.writeContentStream(applicationData, file.getCanonicalPath());
                    // 添加水印
                    WatermarkUtil.addWatermark(file, name);
                    CusDocService.update(doc, applicationData,file);
                    // 删除临时文件
                    file.deleteOnExit();
                }
            }
        } catch (WTException e) {
            throw new RuntimeException(e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    /**
     * 更新文档
     * @param doc
     * @param oldAppData
     * @param pngFile
     */
    public static void update(WTDocument doc, ApplicationData oldAppData, File pngFile) {
        Transaction tx = new Transaction();
        try {
            tx.start();
            ContentHolder holder = ContentHelper.service.getContents(doc);
            ContentItem ci = ((FormatContentHolder) holder).getPrimary();
            ContentServerHelper.service.deleteContent(doc, ci);
            ApplicationData appData = ApplicationData.newApplicationData(doc);

            FileInputStream fileStream = new FileInputStream(pngFile);
            appData.setFileName(pngFile.getName());
            appData.setFileSize(pngFile.length());
            appData.setUploadedFromPath(pngFile.getPath());
            WTPrincipal currentUser = SessionHelper.manager.getPrincipal();
            WTPrincipalReference principalReference = newWTPrincipalReference(currentUser);
            appData.setCreatedBy(principalReference);
            appData.setDescription("Updating PrimaryContent");
            ContentServerHelper.service.updatePrimary(doc, appData, fileStream);
            doc.setFormat(oldAppData.getFormat());
            PersistenceServerHelper.manager.update(doc);
            tx.commit();
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (WTException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            tx.rollback();
        }
    }

    /**
     * 根据编号查询part
     *
     * @param number
     * @return WTPart
     */
    @SuppressWarnings("deprecation")
    public static WTPart getWTPartByNumber(String number) {
        WTPart result = null;
        QueryResult qr = null;
        try {
            QuerySpec qs = new QuerySpec(WTPart.class);
            SearchCondition scnumber = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL,
                    number.toUpperCase());
            qs.appendSearchCondition(scnumber);
            qs.appendAnd();
            SearchCondition sclatest = VersionControlHelper.getSearchCondition(WTPart.class, true);
            qs.appendSearchCondition(sclatest);
            qr = PersistenceHelper.manager.find(qs);
            LatestConfigSpec cfg = new LatestConfigSpec();
            QueryResult qr1 = cfg.process(qr);
            if (qr1 != null && qr1.hasMoreElements()) {
                result = (WTPart) qr1.nextElement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 创建部件和文档间的关系 说明方文档/参考文档 不升级版本
     *
     * @param part
     * @param
     */
    public static void createDocPartLink(WTPart part, List<WTDocument> docList, String actionType) {
        try {
            switch (actionType) {
                case "Describe":
                    // 创建说明方文档关联关系
                    part = (WTPart)checkoutObj(part);
                    for (WTDocument doc : docList) {
                        WTPartDescribeLink describeLink = WTPartDescribeLink.newWTPartDescribeLink(part, doc);
                        PersistenceServerHelper.manager.insert(describeLink);
                    }
                    checkinObj(part);
                    break;
                case "Reference":
                    // 创建参考文档关联关系
                    part = (WTPart)checkoutObj(part);
                    for (WTDocument doc : docList) {
                        WTPartReferenceLink ref_link = WTPartReferenceLink.newWTPartReferenceLink(part,
                                (WTDocumentMaster) doc.getMaster());
                        PersistenceServerHelper.manager.insert(ref_link);
                    }
                    checkinObj(part);
                    break;
                default:
                    break;
            }
        } catch (WTException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检出对象
     *
     * @param
     * @return Workable
     */
    public static Workable checkoutObj(Workable workable) {
        if (workable == null) {
            return null;
        }
        if (WorkInProgressHelper.isWorkingCopy(workable)) {
            return workable;
        }
        try {
            Folder folder = WorkInProgressHelper.service.getCheckoutFolder();
            CheckoutLink checkoutLink = WorkInProgressHelper.service.checkout(workable, folder, "AutoCheckOut");
            workable = checkoutLink.getWorkingCopy();
            if (!WorkInProgressHelper.isWorkingCopy(workable)) {
                workable = WorkInProgressHelper.service.workingCopyOf(workable);
            }
        } catch (WTException e) {
            e.printStackTrace();
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        }
        return workable;
    }

    /**
     * 检入对象
     *
     * @param workable
     * @return Workable
     */
    public static Workable checkinObj(Workable workable) {
        try {
            if (workable == null) {
                return null;
            }
            workable = (WTPart) WorkInProgressHelper.service.checkin(workable, null);
        } catch (WorkInProgressException e) {
            e.printStackTrace();
        } catch (WTPropertyVetoException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (WTException e) {
            e.printStackTrace();
        }
        return workable;
    }



    /**
     * 判断文档和部件的版本
     * @param revisionControlled
     * @return
     */
    public static String getVersion(RevisionControlled revisionControlled) {
        return revisionControlled.getVersionInfo().getIdentifier().getValue() + "."
                + revisionControlled.getIterationInfo().getIdentifier().getValue();
    }

    /**
     * 将String的集合的对象拼接、并输出成字符串
     * @param list
     * @return
     */
    public static String appendStringList(List<String> list) {
        // 创建一个StringBuilder对象来拼接字符串
        StringBuilder stringBuilder = new StringBuilder();

        // 遍历列表并拼接字符串
        for (int i = 0; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i)).append(",");
        }
        // 添加最后一个元素，不加逗号
        stringBuilder.append(list.get(list.size() - 1));
        return stringBuilder.toString();
    }
}
