package com.james.mall.bean;

import lombok.Data;

@Data
public class VisitPage {
    private long id;
    private long uid;
    private String pageName;
    private String keyword;
    private String city;
    private String created;
    private String updated;
}
