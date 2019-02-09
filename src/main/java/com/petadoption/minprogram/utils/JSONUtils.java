package com.petadoption.minprogram.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * json转换类
 */
public class JSONUtils {
    public static String obj2json(Object obj) throws Exception {
        return JSON.toJSONString(obj);
    }

    public static <T> T json2obj(String jsonStr, Class<T> clazz) throws Exception {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     */
    public static <T> T jsonString2Object(String result, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        return JSONObject.toJavaObject(jsonObject, clazz);
    }

    public static <T> Map<String, Object> json2map(String jsonStr) throws Exception {
        return JSON.parseObject(jsonStr, Map.class);
    }

    public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) throws Exception {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    /**
     * JSON字符串对象解析成java List对象
     */
    public static <T> ArrayList<T> jsonStringList2Object(String resultList, Class<T> clazz) {
        JSONArray jsonArray = JSONArray.parseArray(resultList);  //返回数据解析成JSONArray对象
        return (ArrayList<T>) jsonArray.toJavaList(clazz);    //JSON对象解析成java List对象
    }
}
