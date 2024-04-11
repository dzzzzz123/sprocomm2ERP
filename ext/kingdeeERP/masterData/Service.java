package ext.kingdeeERP.masterData;

import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import wt.part.WTPart;

import java.io.IOException;
import java.util.ArrayList;

public class Service {

    static Logger logger = Logger.getLogger(Service.class.getName());

    /**
     * 发送单个部件
     *
     * @param part 部件
     * @return {@link String} 错误信息
     */
    public static String sendSinglePart2ERP(WTPart part) {
        String result;
        Entity entity = getEntity(part);
        String json = parseJson(entity);
        System.out.println(json);
        result = CommonUtil.requestInterface(Config.PartUrl(), "", "", json, "POST", null);
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
        parameters[0] = "BD_MATERIAL";
        parameters[1] = getSubEntity(part);
        entity.setParameters(parameters);
        return entity;
    }


    private static Entity.SubEntity getSubEntity(WTPart part) {
        Entity.SubEntity entity = new Entity.SubEntity();
        entity.setIsDeleteEntry("true");
        entity.setSubSystemId("");
        entity.setIsVerifyBaseDataField("false");
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
        model.setFCreateOrgId(fillFCreateOrgIdEntity(part));
        model.setFUseOrgId(fillFUseOrgIdEntity(part));
        model.setFNumber(part.getNumber());
        model.setFName(part.getName());
        model.setFSpecification(Config.Specification(part));
        model.setFDescription(Config.Description(part));
        model.setfSupplierFullName(Config.SupplierFullName(part));
        model.setfIsCheckSample(processBoolean(Config.IsCheckSample(part)) ? "Y" : "N");
        model.setfClassification(Config.Classification(part));
        model.setFMnemonicCode(Config.MnemonicCode(part));
        model.setFMaterialGroup(fillFMaterialGroupEntity(part));
        model.setSubHeadEntity(fillSubHeadEntity(part));
        model.setSubHeadEntity3(fillSubHeadEntity3(part));
        model.setSubHeadEntity4(fillSubHeadEntity4(part));
        model.setSubHeadEntity5(fillSubHeadEntity5(part));
        entity.setModel(model);
        return entity;
    }

    private static Entity.SubEntity.Model.FCreateOrgId fillFCreateOrgIdEntity(WTPart part) {
        Entity.SubEntity.Model.FCreateOrgId fCreateOrgId = new Entity.SubEntity.Model.FCreateOrgId();
        fCreateOrgId.setFNumber("100");
        return fCreateOrgId;
    }

    private static Entity.SubEntity.Model.FUseOrgId fillFUseOrgIdEntity(WTPart part) {
        Entity.SubEntity.Model.FUseOrgId fUseOrgId = new Entity.SubEntity.Model.FUseOrgId();
        fUseOrgId.setFNumber("100");
        return fUseOrgId;
    }

    private static Entity.SubEntity.Model.FMaterialGroup fillFMaterialGroupEntity(WTPart part) {
        Entity.SubEntity.Model.FMaterialGroup fMaterialGroup = new Entity.SubEntity.Model.FMaterialGroup();
        fMaterialGroup.setFNumber(Config.MaterialGroup(part));
        return fMaterialGroup;
    }

    private static Entity.SubEntity.Model.SubHeadEntity fillSubHeadEntity(WTPart part) {
        Entity.SubEntity.Model.SubHeadEntity subHeadEntity = new Entity.SubEntity.Model.SubHeadEntity();
        subHeadEntity.setFErpClsID(Config.processSource(part));

        Entity.SubEntity.Model.SubHeadEntity.FBaseUnitId fBaseUnitId = new Entity.SubEntity.Model.SubHeadEntity.FBaseUnitId();
        fBaseUnitId.setFNumber(Config.BaseUnitId(part));
        subHeadEntity.setFBaseUnitId(fBaseUnitId);

        return subHeadEntity;
    }

    private static Entity.SubEntity.Model.SubHeadEntity3 fillSubHeadEntity3(WTPart part) {
        Entity.SubEntity.Model.SubHeadEntity3 subHeadEntity3 = new Entity.SubEntity.Model.SubHeadEntity3();
        Entity.SubEntity.Model.SubHeadEntity3.FDefaultVendor fDefaultVendor = new Entity.SubEntity.Model.SubHeadEntity3.FDefaultVendor();
        fDefaultVendor.setFNumber(Config.DefaultVendor(part));
        subHeadEntity3.setfDefaultVendor(fDefaultVendor);
        return subHeadEntity3;
    }

    private static Entity.SubEntity.Model.SubHeadEntity4 fillSubHeadEntity4(WTPart part) {
        Entity.SubEntity.Model.SubHeadEntity4 subHeadEntity4 = new Entity.SubEntity.Model.SubHeadEntity4();
        subHeadEntity4.setFPlanningStrategy(Config.PlanningStrategy(part));
        return subHeadEntity4;
    }

    private static Entity.SubEntity.Model.SubHeadEntity5 fillSubHeadEntity5(WTPart part) {
        Entity.SubEntity.Model.SubHeadEntity5 subHeadEntity5 = new Entity.SubEntity.Model.SubHeadEntity5();
        subHeadEntity5.setFlosspercent(0);
        boolean value = processBoolean(Config.IsKitting(part));
        subHeadEntity5.setFIsKitting(value);
        subHeadEntity5.setFIsCompleteSet(value);
        return subHeadEntity5;
    }

    private static boolean processBoolean(String value) {
        return value.equals("true") || value.equals("1") || value.equals("是");
    }


    private static String parseJson(Entity entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (IOException e) {
            logger.error("发送部件到ERP,转换json出错", e);
        }
        return "";
    }

}
