package com.james.mall.bean;

import com.james.mall.util.StringUtil;
import lombok.Data;
import lombok.Setter;

@Data
public class VerificationCode {
    private Long id;
    private String phone;
    private Integer code;
    private Integer type;//比如 1 注册 ,2 是忘记密码 ,3是抢购,不同类型不可通用
    private boolean valid;//是否有效,比如已经使用过一次算失效
    private String created;
    private String updated;


    public void setCreated(String created) {
        this.created = StringUtil.replaceDotZero(created);
    }

    public void setUpdated(String updated){
        this.updated = StringUtil.replaceDotZero(updated);
    }
}