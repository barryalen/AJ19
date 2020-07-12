package utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Barry
 * @Date 2020/6/13
 */
public class HttpUtils {
    public static Logger logger = Logger.getLogger(HttpUtils.class);

    /**
     * 请求并处理响应
     * @param method        请求方法，get/post/patch
     * @param url           请求路径
     * @param headers       请求头map对象
     * @param params        请求参数，json字符串
     * @param contentType   内容类型，json/form
     */
    public static HttpResponse request(String method, String url, Map<String, String> headers, String params, String contentType){
        // 处理请求头contentType，如果是json类型，请求参数不动，若是form类型，则参数转成form格式且contentType修改
        if("json".equalsIgnoreCase(contentType)){
            headers.put("Content-Type","application/json");
        }else if("form".equalsIgnoreCase(contentType)){
            headers.put("Content-Type","application/x-www-form-urlencoded");
            params = json2KeyValue(params);
        }
        if("get".equalsIgnoreCase(method)){
            if("json".equalsIgnoreCase(contentType)){
                params = json2KeyValue(params);
            }
            return get(url, headers, params);
        }else if("post".equalsIgnoreCase(method)){
            return post(url, headers, params);
        }else if("patch".equalsIgnoreCase(method)){
            return patch(url, headers, params);
        }else{
            System.out.println("给定的请求方法错误，请检查");
            System.out.println("method = " + method + ", url = " + url + ", params = " + params + ", contentType = " + contentType);
        }
        return null;
    }

    /**
     * 处理请求头，传入一个请求头json字符串，返回Header数组
     * @param jsonStr    传入请求头json字符串
     * @return
     * 待废弃
     */
    public static Header[] header(String jsonStr){
        Map<String, String> map = JSONObject.parseObject(jsonStr, Map.class);
        Set<String> set = map.keySet();
        Header[] header = new Header[set.size()];
        int index = 0;
        for (String o : set) {
            header[index] = new BasicHeader(o, map.get(o));
            index++;
        }
        return header;
    }

    /**
     * 处理请求参数， json格式转换form格式
     * @param jsonStr     json格式参数
     * @return 字符串
     */
    public static String json2KeyValue(String jsonStr){
        String keyValue = "";
        try{
            Map map = JSONObject.parseObject(jsonStr, Map.class);
            Set set = map.keySet();
            for (Object o : set) {
                keyValue = keyValue+ o + "=" + map.get(o) + "&";
            }
            keyValue = keyValue.substring(0, keyValue.length()-1);
        }catch (JSONException e){
            keyValue = jsonStr;
            System.out.println("传递的参数不是json格式，请检查");
            System.out.println(e);
        }
        return keyValue;
    }

    /**
     * 添加请求头
     * @param headers       header map对象
     * @param request       请求
     */
    public static void addHeader(Map<String, String> headers, HttpRequest request) {
        Set<String> headerNames = headers.keySet();
        for (String name : headerNames) {
            request.addHeader(name, headers.get(name));
        }
    }

    /**
     * 处理请求响应, 返回响应
     * @param httpResponse  响应
     */
    public static Map<String, Object> response(HttpResponse httpResponse){
        // 响应体
        HttpEntity responseBody = httpResponse.getEntity();
        // 响应头
        Header[] responseHeaders = httpResponse.getAllHeaders();
        // 响应状态码
        StatusLine responseStatus = httpResponse.getStatusLine();
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            response.put("body", EntityUtils.toString(responseBody));
            response.put("header",responseHeaders);
            response.put("status", responseStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送get请求，并返回响应HTTPResponse
     * @param url
     *        url 必须带参数，如果不带不会自动携带参数。
     *        url?KEY=VALUE&KEY2=VALUE2
     *        url/xxx/yyy/2/zzz
     * @param headers     请求头，map对象
     * @param params      暂定
     */
    public static HttpResponse get(String url, Map<String, String> headers, String params){
        logger.info("====getHttp=url==" + url);
        logger.info("====getHttp=params==" + params);
        url = url + "?" +params;
        // 新建请求对象
        HttpClient client = HttpClients.createDefault();
        // 新建请求方法
        HttpGet get = new HttpGet(url);
        // 添加请求头
        addHeader(headers, get);
        try {
            HttpResponse httpResponse = client.execute(get);
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送一个post请求，并返回响应HTTPResponse
     * @param url           请求路径
     * @param headers       请求头，map对象
     * @param params        请求参数，json字符串
     */
    public static HttpResponse post(String url, Map<String, String> headers, String params){
        //HttpHost proxy = new HttpHost("127.0.0.1",8888);
        // 新建请求对象
        HttpClient client = HttpClients.createDefault();
        // 新建请求方法
        HttpPost post = new HttpPost(url);
        // 添加请求头
        addHeader(headers, post);
        // 添加请求参数
        HttpEntity httpEntity = new StringEntity(params, "utf-8");
        post.setEntity(httpEntity);
        // 发送请求
        try {
            // 连接fiddler写法
            //HttpResponse httpResponse = client.execute(proxy,post);
            HttpResponse httpResponse = client.execute(post);
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送一个patch请求，并返回响应HTTPResponse
     * @param url           请求路径
     * @param headers       请求头，map对象
     * @param params        请求参数，json字符串
     */
    public static HttpResponse patch(String url, Map<String, String> headers, String params){
        // 新建请求对象
        HttpClient client = HttpClients.createDefault();
        // 新建请求方法
        HttpPatch patch = new HttpPatch(url);
        // 添加请求头
        addHeader(headers, patch);
        // 添加请求参数
        HttpEntity httpEntity = new StringEntity(params, "utf-8");
        patch.setEntity(httpEntity);
        // 发送请求
        try {
            HttpResponse httpResponse = client.execute(patch);
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回响应体
     * @param resp  响应
     * @return  body字符串
     */
    public static String getResponseBody(HttpResponse resp){
        String body = (String)response(resp).get("body");
        logger.info("==========响应体==" + body);
        return body;
    }
}
