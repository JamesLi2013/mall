package com.james.mall.bean;

import lombok.Data;

import javax.validation.Valid;


@Data
public class Friend {
    private Long id;//这是id
    @Valid
    private String name;//TEXT
}
