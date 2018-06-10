package com.james.mall.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * sku表
 */
@Data
public class Sku {

    private long id;
    private long productId;//归属产品id
    private long  price;//宝贝价格 price
    private String color;//销售属性:颜色
    private String meal;//销售属性:套餐

    private String customProps;//自定义属性
    private int  quantity;//宝贝数量 quantity sku库存
    private int saleCount;//销量
    private int status;//状态

    private long created;
    private long updated;

}
