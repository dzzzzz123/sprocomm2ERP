package ext.kingdeeERP;

import ext.kingdeeERP.util.PersistenceUtil;
import ext.kingdeeERP.util.PropertiesUtil;
import wt.part.WTPart;

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
    
    public static String IsCompleteSet(WTPart part) {
        return properties.getValueByKey(part, "IBA.IsCompleteSet");
    }

    public static String IsKitting(WTPart part) {
        return properties.getValueByKey(part, "IBA.IsKitting");
    }

    public static String BaseUnitId(WTPart part) {
        return properties.getValueByKey(part, "IBA.BaseUnitId");
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

}
