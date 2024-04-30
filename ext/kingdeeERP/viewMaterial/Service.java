package ext.kingdeeERP.viewMaterial;

import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import wt.part.WTPart;

public class Service {

    static Logger logger = Logger.getLogger(Service.class);

    /**
     * 获取部件在ERP系统中的ID并判断是否存在部件，如果存在则返回ID
     *
     * @param part 部件对象
     * @return {@link String}
     */
    public static String process(WTPart part) {
        Entity entity = getEntity(part);
        String json = CommonUtil.parseJson(entity);
        System.out.println(json);
        // 获取token
        ext.kingdeeERP.token.Service.process();
        String resultJson = CommonUtil.requestInterface(Config.viewMaterial(), "", "", json, "POST", null);
        return processJson(resultJson);
    }

    public static Entity getEntity(WTPart part) {
        Entity entity = new Entity();
        Object[] parameters = new Object[2];
        parameters[0] = "BD_MATERIAL";
        parameters[1] = getSubEntity(part);
        entity.setParameters(parameters);
        return entity;
    }

    public static Entity.SubEntity getSubEntity(WTPart part) {
        Entity.SubEntity subEntity = new Entity.SubEntity();
        subEntity.setCreateOrgId("0");
        subEntity.setNumber(part.getNumber());
        subEntity.setId("");
        subEntity.setIsSortBySeq("false");
        return subEntity;
    }

    /**
     * 解析ERP返回的json内容，判断是否查询到了物料，并返回查询得到的物料ID
     *
     * @param resultJson ERP JSON
     * @return {@link String}
     */
    private static String processJson(String resultJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(resultJson);
            JsonNode isSuccessNode = rootNode.path("Result").path("ResponseStatus").path("IsSuccess");
            if (isSuccessNode.asBoolean()) {
                JsonNode IdNode = rootNode.path("Result").path("Result").path("Id");
                return IdNode.asText();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("处理查询ERP中物料id接口返回的json时出错", e);
        }
        return null;
    }
}