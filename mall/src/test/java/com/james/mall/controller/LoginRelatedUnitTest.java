package com.james.mall.controller;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lqx on 2017/12/23.
 */

public class LoginRelatedUnitTest {
    private static String phone = "13312345678";
    private static String password = "123456789";
    private static String verificationCode = "123456";
    private static String deviceId = "16c1ee7edd729345e0f891570c4603b0";

    @Test
    public void verificationCodeFromRegister(){//注册验证码
        HttpTestUtil.get("/auth/smscode?method=registerAccount&phone=" + phone);
    }

    @Test
    public void registerByUser(){//注册
        Map<String,String> map=new HashMap<>();
        map.put("username",phone);
        map.put("password",password);
        map.put("code",verificationCode);
        map.put("platform","0");
        map.put("deviceId", deviceId);
        map.put("phoneModel","1505-A01");
        map.put("appVersion","1.0.0");
        HttpTestUtil.post("/auth/register",map);
    }

    @Test
    public void registerByUserImport(){//注册
        Map<String,String> map=new HashMap<>();
        map.put("username",phone);
        map.put("password",password);
        map.put("code",verificationCode);
        map.put("platform","0");
        map.put("deviceId", deviceId);
        map.put("phoneModel","1505-A01");
        map.put("appVersion","1.0.0");
        HttpTestUtil.post("/auth/register/import",map);
    }

    @Test
    public void loginByUser(){//登录
        Map<String,String> map=new HashMap<>();
        map.put("username",phone);
        map.put("password",password);
        map.put("code",verificationCode);
        map.put("platform","0");
        map.put("deviceId", deviceId);
        map.put("phoneModel","1505-A01");
        map.put("promoCode","SL6IXI");
        map.put("appVersion","1.0.0");
        HttpTestUtil.post("/auth/login",map);
    }

    @Test
    public void updatePersonInfoTest(){
        HashMap<String,String> map = new HashMap<>();
        map.put("nickname","");
        map.put("sex","2");
        HttpTestUtil.postMult("/auth/personInfo/update",map,"D:\\opt\\img\\banner_min2.jpg");
    }


    @Test
    public void authTest(){//auth验证
        HttpTestUtil.get("/friend/all");
    }
    @Test
    public void getTest(){
        HttpTestUtil.get("https://cloud.tencent.com/document/product/382/13613");
    }

    @Test
    public void uploadSingleFileTest(){
        HttpTestUtil.postMult("/backup",new HashMap<String,String>(),"e:/complete0103.zip");
//        HttpTestUtil.postMult("/newSingleUpload",new HashMap<String,String>(),"e:/complete2.zip");
    }


}
