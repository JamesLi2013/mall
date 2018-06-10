package com.james.mall.bean;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;//登录用户名
    private String phone;//手机号
    private String nickname;//昵称
    private String head;//头像
    private String promoCode;//推广码
    private Boolean firstLogin;
    private Integer sex;//0 没有设置;1为男性;2为女性

    private String accessToken;
    private String refreshToken;



}
