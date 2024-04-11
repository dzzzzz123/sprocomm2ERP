package ext.kingdeeERP.token;

import ext.kingdeeERP.Config;
import ext.kingdeeERP.util.CommonUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    static Logger logger = Logger.getLogger(Service.class.getName());

    /**
     * 获取ERP的token
     */
    public static void process() {
        Entity entity = new Entity();
        entity.setParam1(Config.TokenParam1());
        entity.setUsername(Config.TokenUsername());
        entity.setPassword(Config.TokenPassword());
        entity.setParam2(Config.TokenParam2());
        String json = getJson(entity);
        CommonUtil.requestInterface(Config.TokenUrl(), "", "", json, "POST", null);
    }

    /**
     * 将实体类转换为json格式
     *
     * @param entity 实体类
     * @return {@link String} json
     */
    private static String getJson(Entity entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> rootMap = new HashMap<>();
        List<String> parameters = new ArrayList<>();
        parameters.add(entity.getParam1());
        parameters.add(entity.getUsername());
        parameters.add(entity.getPassword());
        parameters.add(entity.getParam2());
        rootMap.put("parameters", parameters);
        try {
            return objectMapper.writeValueAsString(rootMap);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }
    
}
