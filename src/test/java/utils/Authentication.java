package utils;

import com.alibaba.fastjson.JSONPath;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
    // 存储键值对
    public static Map<String, Object> VARS = new HashMap<String, Object>();

    /**
     * 使用jsonpath获取内容并存储到VARS中
     * @param json          json字符串
     * @param jsonPath      jsonpath表达式 eg："$.data.token_info.token"
     * @param key           标识符
     */
    public static void json2Vars(String json, String jsonPath, String key){
        Object read = JSONPath.read(json, jsonPath);
        if (read != null){
            Authentication.VARS.put(key, read);
        }
    }

}
