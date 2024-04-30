package ext.kingdeeERP.BOM.service;

import ext.kingdeeERP.BOM.entity.Entity;
import ext.kingdeeERP.BOM.entity.SubPartEntity;
import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;

import java.util.ArrayList;
import java.util.List;

public class Service {

    final static Logger logger = LogManager.getLogger(Service.class);

    /**
     * 发送单个部件
     *
     * @param part 部件
     * @return {@link String} 错误信息
     */
    public static String sendSingleBOM2ERP(WTPart part) {
        String result;
        Entity entity = getEntity(part);
        String json = CommonUtil.parseJson(entity);
        System.out.println(json);
        result = CommonUtil.requestInterface(Config.BOMUrl(), "", "", json, "POST", null);
        return result;
    }

    /**
     * 将部件转换为实体类
     *
     * @param part 部件
     * @return {@link Entity} 实体类
     */
    private static Entity getEntity(WTPart part) {
        Entity entity = new Entity();
        Object[] parameters = new Object[2];
        parameters[0] = "ENG_BOM";
        parameters[1] = getSubEntity(part);
        entity.setParameters(parameters);
        return entity;
    }

    private static Entity.SubEntity getSubEntity(WTPart part) {
        Entity.SubEntity entity = new Entity.SubEntity();
        entity.setIsDeleteEntry("true");
        entity.setSubSystemId("");
        entity.setIsVerifyBaseDataField("true");
        entity.setIsEntryBatchFill("true");
        entity.setValidateFlag("true");
        entity.setNumberSearch("true");
        entity.setIsAutoAdjustField("false");
        entity.setInterationFlags("");
        entity.setIgnoreInterationFlag("");
        entity.setIsControlPrecision("false");
        entity.setValidateRepeatJson("false");
        entity.setIsAutoSubmitAndAudit("true");
        entity.setNeedReturnFields(new ArrayList<>());
        entity.setNeedUpDateFields(new ArrayList<>());

        // 填充 Model 对象的属性
        Entity.SubEntity.Model model = new Entity.SubEntity.Model();
        model.setfID("0");
        model.setFCreateOrgId(fillFCreateOrgIdEntity());
        model.setFUseOrgId(fillFUseOrgIdEntity());
        model.setFNumber(part.getNumber() + "_" + part.getVersionInfo().getIdentifier().getValue());
        model.setFName(part.getName());
        model.setfBillType(fillFBillTypeEntity());
        model.setfBomCategory(Config.BomCategory());
        model.setfMaterialId(fillFMaterialIdEntity(part));
        model.setfNote(Config.Note(part));
        model.setfTreeEntity(fillSubPartEntity(part));
        entity.setModel(model);
        return entity;
    }

    /**
     * 获取BOM子件 部件对象所映射的实体类对象
     *
     * @param part BOM父组件
     * @return {@link Object[]}
     */
    private static Object[] fillSubPartEntity(WTPart part) {
        List<SubPartEntity> subPartEntities = new ArrayList<>();
        List<WTPart> subParts = CommonUtil.getBomByPart(part);
        // 发送BOM之前先发送BOM头到ERP中
        ext.kingdeeERP.masterData.Service.sendSinglePart2ERP(part);
        for (int i = 0; i < subParts.size(); i++) {
            // 发送BOM之前先发送BOM中的所有物料到ERP中
            ext.kingdeeERP.masterData.Service.sendSinglePart2ERP(subParts.get(i));
            subPartEntities.addAll(fillSingleSubPartEntity(part, subParts.get(i), i + 1));
        }
        return subPartEntities.toArray();
    }

    private static List<SubPartEntity> fillSingleSubPartEntity(WTPart part, WTPart subPart, int replaceGroup) {
        List<SubPartEntity> subPartEntities = new ArrayList<>();
        // 获取主料内容
        WTPartUsageLink link = CommonUtil.getLinkByPart(part, subPart);
        String[] fraction = SubPartEntityService.getFraction(link);
        SubPartEntity masterPartEntity = SubPartEntityService.getMasterPartEntity(subPart, link);
        // 获取替代料内容
        List<WTPartSubstituteLink> substituteLinks = CommonUtil.getSubstituteLinks(link);
        if (substituteLinks != null && !substituteLinks.isEmpty()) {
            for (int i = 0; i < substituteLinks.size(); i++) {
                // 获取替代料关联关系
                WTPartSubstituteLink wtPartSubstituteLink = substituteLinks.get(i);
                // 获取替代部件
                WTPart substitutePart = CommonUtil.getWTPartByMaster((WTPartMaster) wtPartSubstituteLink.getRoleBObject());
                // 发送BOM之前先发送替代料到ERP中
                ext.kingdeeERP.masterData.Service.sendSinglePart2ERP(substitutePart);
                SubPartEntity substitutePartEntity = SubPartEntityService.getSubstitutePartEntity(substitutePart, wtPartSubstituteLink);
                substitutePartEntity.setfReplaceGroup(String.valueOf(replaceGroup));
                substitutePartEntity.setfReplacePriority(String.valueOf(i + 1));
                substitutePartEntity.setfNumberator(fraction[0]);
                substitutePartEntity.setfDenominator(fraction[1]);
                subPartEntities.add(substitutePartEntity);
            }
        } else {
            masterPartEntity.setfReplacePriority("");
            masterPartEntity.setfReplaceType("");
            masterPartEntity.setfReplacePolicy("");
            masterPartEntity.setfIskeyItem("false");
        }
        masterPartEntity.setfNumberator(fraction[0]);
        masterPartEntity.setfDenominator(fraction[1]);
        masterPartEntity.setfReplaceGroup(String.valueOf(replaceGroup));
        subPartEntities.add(masterPartEntity);
        return subPartEntities;
    }

    private static Entity.SubEntity.Model.FCreateOrgId fillFCreateOrgIdEntity() {
        Entity.SubEntity.Model.FCreateOrgId fCreateOrgId = new Entity.SubEntity.Model.FCreateOrgId();
        fCreateOrgId.setFNumber("100");
        return fCreateOrgId;
    }

    private static Entity.SubEntity.Model.FUseOrgId fillFUseOrgIdEntity() {
        Entity.SubEntity.Model.FUseOrgId fUseOrgId = new Entity.SubEntity.Model.FUseOrgId();
        fUseOrgId.setFNumber("100");
        return fUseOrgId;
    }

    private static Entity.SubEntity.Model.FBILLTYPE fillFBillTypeEntity() {
        Entity.SubEntity.Model.FBILLTYPE fBilltype = new Entity.SubEntity.Model.FBILLTYPE();
        fBilltype.setFNumber(Config.BillType());
        return fBilltype;
    }

    private static Entity.SubEntity.Model.FMATERIALID fillFMaterialIdEntity(WTPart part) {
        Entity.SubEntity.Model.FMATERIALID fMaterialId = new Entity.SubEntity.Model.FMATERIALID();
        fMaterialId.setFNumber(part.getNumber());
        return fMaterialId;
    }
}
