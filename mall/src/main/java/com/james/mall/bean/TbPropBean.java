package com.james.mall.bean;

/**
 * Created by lqx on 2017/12/20.
 */

public class TbPropBean {

    /**
     * async : {}
     * text : 触摸板
     * value : 24312922
     */

    private String async;
    private String text;
    private String value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }
}
