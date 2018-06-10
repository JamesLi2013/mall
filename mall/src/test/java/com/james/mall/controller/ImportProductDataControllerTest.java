package com.james.mall.controller;

import org.junit.Test;

public class ImportProductDataControllerTest {

    @Test
    public void insertIdReturn(){
        HttpTestUtil.get("/propkey/test");
    }

    @Test
    public void importPropKeyTest(){
//        HttpTestUtil.get("/product/propKey");
//        HttpTestUtil.get("/product/mealValue");
    }

    @Test
    public void importProductTest(){
        //第一次调用时,需要依次调用importPropKeyTest()方法中的2条
        HttpTestUtil.get("/product/import");
    }
}
