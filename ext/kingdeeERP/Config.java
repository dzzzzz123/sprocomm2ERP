package ext.kingdeeERP;

import ext.kingdeeERP.util.PersistenceUtil;
import ext.kingdeeERP.util.PropertiesUtil;
import wt.part.WTPart;
import wt.part.WTPartSubstituteLink;
import wt.part.WTPartUsageLink;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Config {

    private static PropertiesUtil properties = PropertiesUtil.getInstance("config.properties");

    public static String getValue(String key) {
        return properties.getValueByKey(key);
    }

    public static String TokenUrl() {
        return properties.getValueByKey("token.url");
    }

    public static String PartUrl() {
        return properties.getValueByKey("part.url");
    }

    public static String TokenParam1() {
        return properties.getValueByKey("token.param1");
    }

    public static String TokenParam2() {
        return properties.getValueByKey("token.param2");
    }

    public static String TokenUsername() {
        return properties.getValueByKey("erp.username");
    }

    public static String TokenPassword() {
        return properties.getValueByKey("erp.password");
    }

    public static String Specification(WTPart part) {
        return properties.getValueByKey(part, "IBA.Specification");
    }

    public static String Description(WTPart part) {
        return properties.getValueByKey(part, "IBA.Description");
    }

    public static String SupplierFullName(WTPart part) {
        return properties.getValueByKey(part, "IBA.SupplierFullName");
    }

    public static String IsCheckSample(WTPart part) {
        return properties.getValueByKey(part, "IBA.IsCheckSample");
    }

    public static String Classification(WTPart part) {
        return properties.getValueByKey(part, "IBA.Classification");
    }

    public static String DefaultVendor(WTPart part) {
        return properties.getValueByKey(part, "IBA.DefaultVendor");
    }

    public static String PurchaseOrgId() {
        return properties.getValueByKey("param.PurchaseOrgId");
    }

    public static String IsKitting(WTPart part) {
        return properties.getValueByKey(part, "IBA.IsKitting");
    }

    public static String MnemonicCode(WTPart part) {
        return properties.getValueByKey(part, "IBA.MnemonicCode");
    }

    public static String processSource(WTPart part) {
        HashMap<String, String> sourceMapping = getMapping("source.mapping");
        String source = part.getSource().toString();
        return sourceMapping.get(source);
    }

    public static String PlanningStrategy(WTPart part) {
        String source = part.getSource().toString();
        String statePatternStr = properties.getValueByKey("planningStrategy.mapping");
        Set<String> statePatterns = new HashSet<>(Arrays.asList(statePatternStr.split("/")));
        return statePatterns.contains(source) ? "0" : "1";
    }

    public static String MaterialGroup(WTPart part) {
        HashMap<String, String> partTypeMapping = getMapping("partType.mapping");
        String partType = PersistenceUtil.getPartTypeDisPlayName(part);
        return partTypeMapping.get(partType);
    }


    public static String BaseUnitId(WTPart part) {
        HashMap<String, String> BaseUnitMapping = getMapping("BaseUnit.mapping");
        String baseUnit = part.getDefaultUnit().toString();
        return BaseUnitMapping.get(baseUnit);
    }

    private static HashMap<String, String> getMapping(String ProKey) {
        HashMap<String, String> sourceMapping = new HashMap<>();
        String statePatternStr = properties.getValueByKey(ProKey);
        String[] statePatterns = statePatternStr.split("/");
        for (String str : statePatterns) {
            String[] pattern = str.split(":");
            sourceMapping.put(pattern[0], pattern[1]);
        }
        return sourceMapping;
    }

    // BOM相关配置读取
    public static String BillType() {
        return properties.getValueByKey("param.BillType");
    }

    public static String BomCategory() {
        return properties.getValueByKey("param.BomCategory");
    }

    public static String BOMUrl() {
        return properties.getValueByKey("BOM.url");
    }

    public static String Note(WTPart part) {
        return properties.getValueByKey(part, "IBA.Note");
    }

    public static String IsSkip(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.IsSkip");
    }

    public static String EffectDate() {
        return properties.getValueByKey("BOM.EffectDate");
    }

    public static String ExpireDate() {
        return properties.getValueByKey("BOM.ExpireDate");
    }

    public static String IsGetScrap(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.IsGetScrap");
    }

    public static String IsKeyComponent(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.IsKeyComponent");
    }

    public static String IsKeyComponent(WTPartSubstituteLink link) {
        return properties.getValueByKey(link, "IBA.IsKeyComponent");
    }

    public static String IsCustomerService(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.IsCustomerService");
    }

    public static String IsByCust(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.IsByCust");
    }

    public static String ItemNote(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.ItemNote");
    }

    public static String PositionNo(WTPartUsageLink link) {
        return properties.getValueByKey(link, "IBA.PositionNo");
    }

    public static String OwnerId(WTPart part) {
        return properties.getValueByKey(part, "IBA.OwnerId");
    }

    public static String DefaultAcctNO() {
        return properties.getValueByKey("DefaultAcctNO");
    }

    public static void setPart2ERPStatus(WTPart part, String IBAValue) {
        properties.setValueByKey(part, "IBA.Part2ERPStatus", IBAValue);
    }

    public static void setBOM2ERPStatus(WTPart part, String IBAValue) {
        properties.setValueByKey(part, "IBA.BOM2ERPStatus", IBAValue);
    }

    public static void setERPErrorMsg(WTPart part, String IBAValue) {
        properties.setValueByKey(part, "IBA.ERPErrorMsg", IBAValue);
    }

    public static String oracleDriver() {
        return properties.getValueByKey("sql.oracleDriver");
    }

    public static String oracleUrl() {
        return properties.getValueByKey("sql.url");
    }

    public static String oracleUserName() {
        return properties.getValueByKey("sql.UserName");
    }

    public static String oraclePassWord() {
        return properties.getValueByKey("sql.PassWord");
    }

    public static String viewMaterial() {
        return properties.getValueByKey("view.material.url");
    }

    public static String InternalName() {
        return properties.getValueByKey("checkIn.noPermission.InternalName");
    }

    public static Set<String> PermissionPartState() {
        Set<String> stateSet = new HashSet<>();
        String[] statePatterns = properties.getValueByKey("checkIn.permission.State").split("/");
        for (String str : statePatterns) {
            stateSet.add(str);
        }
        return stateSet;
    }
}
