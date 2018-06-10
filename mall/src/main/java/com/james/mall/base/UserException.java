package com.james.mall.base;

/**
 * 用户方面导致的异常
 */
public class UserException extends RuntimeException {
    private boolean log;//是否需要被记录

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message,boolean log) {
        super(message);
        this.log = log;
    }

    public boolean isLog(){
        return log;
    }
}
