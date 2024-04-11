package ext.kingdeeERP.util;

import com.sun.jndi.toolkit.chars.BASE64Encoder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import wt.method.RemoteAccess;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CommonUtil implements RemoteAccess {
    
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
//		System.out.println("JSON: " + processJson(json));
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
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = method.equalsIgnoreCase("GET")
                ? restTemplate.exchange(url, HttpMethod.GET, entity, String.class)
                : restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (responseEntity == null) {
            return null;
        }
        String resultJson = responseEntity.getBody().toString();
        System.out.println("RESULTJSON: " + resultJson);
        return resultJson;
    }


}
