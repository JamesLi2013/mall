package com.james.mall.bean;

import lombok.Data;

/**
 * 用户表
 */
@Data
public class User {
    private Long id;
    private String password;//加密后的密码
    private String username;//登录用户名
    private String phone;//手机号
    private String nickname;//昵称
    private String head;//头像
    private String promoCode;//推广码
    private String deviceId;//设备id
    private String platform;//平台
    private String phoneModel;//手机型号
    private String version;//app 版本
    private String salt;//盐
    private Boolean firstLogin;

    private Integer sex;//0 没有设置;1为男性;2为女性
    private String registerTime;
    private String lastLogin;
    private double longitude;//经度
    private double latitude;//纬度
    private String created;
    private String updated;

}
