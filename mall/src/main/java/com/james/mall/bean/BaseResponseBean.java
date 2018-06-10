package com.james.mall.bean;

/**
 * 公共返回bean
 */
public class BaseResponseBean<T> {
    private String retCode;
    private String msg;

    private T result;

    public BaseResponseBean(){

    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
