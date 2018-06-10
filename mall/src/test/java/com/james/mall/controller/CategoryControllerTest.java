package com.james.mall.controller;

import org.junit.Test;

import java.util.HashMap;

public class CategoryControllerTest {

    @Test
    public void findAllTest(){
//        HttpTestUtil.post("/category/findAll",new HashMap<String,String>());
        HttpTestUtil.get("/category/findAll?uid=2004");
    }
}
