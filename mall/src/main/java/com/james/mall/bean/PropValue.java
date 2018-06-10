package com.james.mall.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商品属性值,比如属性名称id为121,pName内存大小,属性值表中vid为12345,vName8G;vid12346 ,vName16G等
 */
@Data
public class PropValue {
    private long id;
    private long pid;//归属的属性名称id
    private String vName;//属性值 value name
    private long created;
    private long updated;
}
