package ext.kingdeeERP.util;

import com.ptc.windchill.uwgm.common.container.OrganizationHelper;
import com.sun.jndi.toolkit.chars.BASE64Encoder;
import ext.kingdeeERP.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.inf.container.OrgContainer;
import wt.inf.container.PrincipalSpec;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.method.MethodContext;
import wt.org.DirectoryContextProvider;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTOrganization;
import wt.part.*;
import wt.pds.StatementSpec;
import wt.pom.WTConnection;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CommonUtil {

    static Logger logger = LogManager.getLogger(CommonUtil.class);

    /**
     * 携带信息并用POST请求外部系统（如SAP，OA）中的某个接口 存在账户和密码时则设置验证否则不设置 map为添加到Headers上的内容，无则填null
     *
     * @param url      访问目标接口的URL
     * @param username 访问目标接口需要验证的用户名
     * @param password 访问目标接口需要验证的密码
     * @param json     携带的json信息
     * @param method   请求的方式 GET/POST
     * @param map      请求头中需要添加的信息,没有填null
     * @return 返回的json信息
     */
    public static String requestInterface(String url, String username, String password, String json, String method,
                                          HashMap<String, String> map) {
        // 输出日志文件
        System.out.println("--------当前执行的请求接口的参数列表--------");
        System.out.println("URL: " + url);
        System.out.println("USERNAME: " + username + " PASSWORD:" + password);
        System.out.println("JSON: " + json);
        System.out.println("METHOD: " + method);
        System.out.println("HEADERS: ");

        RestTemplate restTemplate = new RestTemplate();
        // 添加BASIC认证
        String auth = username + ":" + password;
        String encodedAuth = new BASE64Encoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encodedAuth;

        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // 自定义请求头,添加headers内容
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if (map != null) {
            Set<String> set = map.keySet();
            if (!set.isEmpty()) {
                for (String key : set) {
                    String value = map.get(key);
                    System.out.println("key:" + key + " value:" + map.get(key));
                    headers.add(key, value);
                }
            }
        }
        // 添加json参数,设置请求的方法并获取返回的json信息
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = method.equalsIgnoreCase("GET")
                ? restTemplate.exchange(url, HttpMethod.GET, entity, String.class)
                : restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (responseEntity == null) {
            return null;
        }
        String resultJson = responseEntity.getBody();
        System.out.println("RESULTJSON: " + resultJson);
        return resultJson;
    }

    /**
     * 将对象转换为json内容
     *
     * @param entity 对象
     * @return {@link String}
     */
    public static String parseJson(Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (IOException e) {
            logger.error("发送部件到ERP,转换json出错", e);
        }
        return "";
    }

    /**
     * 处理返回的json中的信息提取需要的错误信息
     *
     * @param json 返回的json
     * @return {@link String}
     */
    public static String processResult(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode isSuccessNode = rootNode.path("Result").path("ResponseStatus").path("IsSuccess");
            if (!isSuccessNode.asBoolean()) {
                JsonNode errorsNode = rootNode.path("Result").path("ResponseStatus").path("Errors");
                if (errorsNode.isArray()) {
                    StringBuilder errorMessageBuilder = new StringBuilder();
                    for (JsonNode errorNode : errorsNode) {
                        String errorMessage = errorNode.path("Message").asText();
                        errorMessageBuilder.append(errorMessage).append("\n");
                    }
                    return errorMessageBuilder.toString();
                }
            } else {
                return "IsSuccess";
            }
        } catch (Exception e) {
            logger.error("处理接口请求信息时出错", e);
        }
        return null;
    }

    /**
     * 连接Windchill数据库来运行自定义sql
     *
     * @return {@link WTConnection}
     */
    public static WTConnection getWTConnection() {
        WTConnection wtconnection = null;
        try {
            MethodContext methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
        } catch (Exception e) {
            logger.error("连接数据库链接时出错", e);
        }
        return wtconnection;
    }


    /**
     * 处理boolean类型的数据
     *
     * @param value 输入值
     * @return boolean
     */
    public static boolean processBoolean(String value) {
        return value.equals("true") || value.equals("1") || value.equals("是");
    }

    /**
     * 不递归获取部件所有的子部件
     *
     * @param parentPart 父部件
     * @return {@link List}<{@link WTPart}>
     */
    public static List<WTPart> getBomByPart(WTPart parentPart) {
        WTPart sPart = null;
        QueryResult qr2 = null;
        List<WTPart> list = new ArrayList<>();
        try {
            QueryResult qr = WTPartHelper.service.getUsesWTPartMasters(parentPart);
            while (qr.hasMoreElements()) {
                WTPartUsageLink usageLink = (WTPartUsageLink) qr.nextElement();
                qr2 = VersionControlHelper.service.allVersionsOf(usageLink.getUses());
                if (qr2.hasMoreElements()) {
                    sPart = (WTPart) qr2.nextElement();
                    list.add(sPart);
                }
            }
        } catch (WTException e) {
            logger.error("查询BOM子组件出错", e);
        }
        return list;
    }

    /**
     * 获取部件间的关联关系
     *
     * @param fatherPart 父部件
     * @param sonPart    子部件
     * @return {@link WTPartUsageLink}
     */
    public static WTPartUsageLink getLinkByPart(WTPart fatherPart, WTPart sonPart) {
        try {
            QueryResult qr = WTPartHelper.service.getUsesWTPartMasters(fatherPart);
            while (qr.hasMoreElements()) {
                WTPartUsageLink link = (WTPartUsageLink) qr.nextElement();
                WTPartMaster partMaster = (WTPartMaster) link.getRoleBObject();
                if (partMaster.getNumber().equals(sonPart.getNumber())) {
                    return link;
                }
            }
        } catch (WTException e) {
            logger.error("查询BOM结构部件中关联关系出错", e);
        }
        return null;
    }

    /**
     * 浮点数转换为分数
     *
     * @param decimal 浮点数
     * @return {@link String[]}
     */
    public static String[] double2Fraction(double decimal) {
        String[] fraction = new String[2];
        BigDecimal bigDecimal = BigDecimal.valueOf(decimal);
        BigInteger numerator = bigDecimal.unscaledValue();
        BigInteger denominator = BigInteger.TEN.pow(bigDecimal.scale());
        BigInteger gcd = numerator.gcd(denominator);

        fraction[0] = String.valueOf(numerator.divide(gcd));
        fraction[1] = String.valueOf(denominator.divide(gcd));
        return fraction;
    }

    /**
     * 根据部件于获取局部替代料的关联关系
     *
     * @param usageLink 部件使用关系
     * @return {@link List}<{@link WTPartSubstituteLink}>
     */
    public static List<WTPartSubstituteLink> getSubstituteLinks(WTPartUsageLink usageLink) {
        if (usageLink == null) {
            return null;
        }
        List<WTPartSubstituteLink> list = new ArrayList<>();
        long masterId = PersistenceHelper.getObjectIdentifier(usageLink).getId();
        int[] index = {0};
        try {
            QuerySpec qs = new QuerySpec(WTPartSubstituteLink.class);
            qs.appendWhere(new SearchCondition(WTPartSubstituteLink.class, "roleAObjectRef.key.id",
                    SearchCondition.EQUAL, masterId), index);
            QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
            while (qr.hasMoreElements()) {
                WTPartSubstituteLink sLink = (WTPartSubstituteLink) qr.nextElement();
                list.add(sLink);
            }
        } catch (QueryException e) {
            logger.error("根据部件使用关系查询替代料关系Query执行出错", e);
        } catch (WTException e) {
            logger.error("根据部件使用关系查询替代料关系出错", e);
        }
        return list;
    }

    /**
     * 根据部件使用关系获取替代物料对象
     *
     * @param usageLink 部件使用关系
     * @return {@link List}<{@link WTPart}>
     */
    public static List<WTPart> getSubstituteParts(WTPartUsageLink usageLink) {
        List<WTPartSubstituteLink> substituteLinks = getSubstituteLinks(usageLink);
        List<WTPart> partList = new ArrayList<>();
        for (WTPartSubstituteLink substituteLink : substituteLinks) {
            partList.add(getWTPartByMaster((WTPartMaster) substituteLink.getRoleBObject()));
        }
        return partList;
    }

    /**
     * 根据WTPartMaster查询最新的WTPart对象
     *
     * @param partMaster WTPartMaster对象
     * @return {@link WTPart}
     */
    public static WTPart getWTPartByMaster(WTPartMaster partMaster) {
        try {
            QueryResult linksQueryResult = VersionControlHelper.service.allVersionsOf(partMaster);
            while ((linksQueryResult.hasMoreElements())) {
                WTPart tempPart = (WTPart) linksQueryResult.nextElement();
                if (tempPart.isLatestIteration()) {
                    return tempPart;
                }
            }
        } catch (WTException e) {
            logger.error("根据部件使用关系查询替代料关系出错", e);
        }
        return null;
    }

    /**
     * 从request中获取字节流的信息将其中的json转换为实体类列表
     *
     * @param <T>            实体类
     * @param request        传递的请求参数
     * @param clazz          实体类类型
     * @param rootNodeString 是否有根节点
     * @return T 实体类列表
     */
    public static <T> List<T> getEntitiesFromRequest(HttpServletRequest request, Class<T> clazz,
                                                     String rootNodeString) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonInput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
            System.out.println("-------从HttpServletRequest获取到的内容-------");
            System.out.println("json: " + jsonInput);
            return getEntitiesFromJson(jsonInput.toString(), clazz, rootNodeString);
        } catch (IOException e) {
            logger.error("根据request对象中的字节流转换为实体类出错", e);
            return Collections.emptyList();
        }
    }

    /**
     * 将json转换为实体类列表
     *
     * @param <T>            实体类
     * @param json           需要转换的json
     * @param clazz          实体类类型
     * @param rootNodeString 是否有根节点
     * @return T 实体类列表
     */
    public static <T> List<T> getEntitiesFromJson(String json, Class<T> clazz, String rootNodeString) {
        try {
            if (StringUtils.isBlank(json)) {
                return Collections.emptyList();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            List<T> entities = new ArrayList<>();
            // 如果没有指定根节点字符串，则直接尝试解析为实体对象
            // 如果指定了根节点字符串，则尝试从根节点中获取指定的节点
            rootNode = StringUtils.isNotBlank(rootNodeString) ? rootNode.get(rootNodeString) : rootNode;
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    T entity = objectMapper.treeToValue(node, clazz);
                    entities.add(entity);
                }
            } else {
                T entity = objectMapper.treeToValue(rootNode, clazz);
                entities.add(entity);
            }
            return entities;
        } catch (IOException e) {
            logger.error("根据json转换维实体类出错", e);
        }
        return Collections.emptyList();
    }

    /**
     * 从QueryResult中获取需要类型的对象数据
     *
     * @param qr    结果集对象
     * @param clazz 想解析得到的列表包含对象
     * @return {@link ArrayList}<{@link T}>
     */
    public static <T> ArrayList<T> getListFromQR(QueryResult qr, Class<T> clazz) {
        ArrayList<T> list = new ArrayList<T>();
        while (qr.hasMoreElements()) {
            Object obj = qr.nextElement();
            if (clazz.isInstance(obj)) {
                T castedObj = clazz.cast(obj);
                list.add(castedObj);
            }
        }
        return list;
    }

    /**
     * 获取指定名称的组对象
     *
     * @param GroupName 组名
     * @return {@link WTGroup }
     */
    public static WTGroup getGroup(String GroupName) {
        WTGroup foundGroup = null;
        try {
            WTOrganization org = OrganizationHelper.getOrganizationByName(Config.ORGName());
            OrgContainer orgContainer = WTContainerHelper.service.getOrgContainer(org);
            PrincipalSpec principalSpec = new PrincipalSpec(WTContainerRef.newWTContainerRef(orgContainer),
                    WTGroup.class);
            principalSpec.setPerformLookup(false);
            principalSpec.setIncludeAllServices(true);
            DirectoryContextProvider[] directoryContextProviders = WTContainerHelper.service
                    .getPublicContextProviders(principalSpec);
            foundGroup = OrganizationServicesHelper.manager.getGroup(GroupName, directoryContextProviders[0]);
        } catch (Exception e) {
            logger.error("获取指定名称的组对象时出错", e);
        }
        return foundGroup;
    }

}
