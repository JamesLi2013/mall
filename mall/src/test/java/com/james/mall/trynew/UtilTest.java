package com.james.mall.trynew;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.mall.bean.BaseResponseBean;
import com.james.mall.util.ResponseUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class UtilTest {
    @Test
    public void appendJsonString(){
        System.out.println(ResponseUtil.getSuccessString("hello"));
        String helloJson = ResponseUtil.getSuccessString("hello");
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(BaseResponseBean.class, String.class);
        try {
            BaseResponseBean<String> baseResponseBean = mapper.readValue(helloJson, javaType);
            System.out.println("1".equals(baseResponseBean.getRetCode()));
            System.out.println("hello".equals(baseResponseBean.getMsg()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
