package com.james.mall.bean;

import lombok.Data;

@Data
public class Category {

    private Long id;
    private String catName;//分类名称
    private Integer catLevel;//分类层级
    private Long parentId;
    private Long bigId;//大分类
    private Long midId;//中分类
    private String created;
    private String updated;

}
