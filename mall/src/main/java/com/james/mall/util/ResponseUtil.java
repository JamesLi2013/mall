package com.james.mall.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.mall.bean.BaseResponseBean;

import java.util.concurrent.ConcurrentHashMap;

public class ResponseUtil {
    public static final int CODE_SUCCESS_COMMON = 1;
    public static final int CODE_FAILED_COMMON = 0;
    public static final String DESC_SUCCESS_COMMON = "success";
    public static final String DESC_FAILED_COMMON = "fail";


    /**
     * 转化为通用返回数据
     */
    public static <T> BaseResponseBean<T> turnData(T t){
        return turnData(CODE_SUCCESS_COMMON,DESC_SUCCESS_COMMON,t);
    }

    public static <T> BaseResponseBean<T> turnData(int code, String desc, T t){
        BaseResponseBean<T> bean = new BaseResponseBean<>();
        bean.setRetCode("" + code);
        bean.setMsg(desc);
        bean.setResult(t);
        return bean;
    }

    /**
     * 返回失败信息
     */
    public static <T> BaseResponseBean<T> turnFailedData(int code ,String desc){
        BaseResponseBean<T> bean = new BaseResponseBean<>();
        bean.setRetCode(""+code);
        bean.setMsg(desc);
        return bean;
    }

    /**
     * 通用返回失败信息
     */
    public static <T> BaseResponseBean<T> turnFailedData(){
        return turnFailedData(CODE_FAILED_COMMON,DESC_FAILED_COMMON);
    }

    /**
     * 通用返回失败信息
     */
    public static <T> BaseResponseBean<T> turnFailedData(String desc){
        return turnFailedData(CODE_FAILED_COMMON,desc);
    }

    /**
     * 通用返回失败信息
     */
    public static String getFailedJsonString(){
        BaseResponseBean<String> bean = new BaseResponseBean<>();
        bean.setRetCode(""+CODE_FAILED_COMMON);
        bean.setMsg(DESC_FAILED_COMMON);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 通用返回成功信息,测试方法已通过,不使用ObjectMapper的原因是节约内存.
     */
    public static String getSuccessString(String message){
        StringBuilder sb =new StringBuilder();
        sb.append("{\"retCode\":\"")
                .append(CODE_SUCCESS_COMMON)
                .append("\",\"msg\":\"")
                .append(message)
                .append("\",\"result\":null}");
        return sb.toString();
    }

    /**
     * 通用返回成功信息,测试方法已通过,不使用ObjectMapper的原因是节约内存.
     */
    public static String getFailedString(String message){
        StringBuilder sb =new StringBuilder();
        sb.append("{\"retCode\":\"")
                .append(CODE_FAILED_COMMON)
                .append("\",\"msg\":\"")
                .append(message)
                .append("\",\"result\":null}");
        return sb.toString();
    }

    public static String getJsonFromBean(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getBeanFromJson(String json, Class<T> valueType){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
