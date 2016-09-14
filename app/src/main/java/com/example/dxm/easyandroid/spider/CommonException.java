package com.example.dxm.easyandroid.spider;

/**
 * Created by duxiaoming on 16/8/20.
 */
public class CommonException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CommonException() {
    }

    public CommonException(String detailMessage) {
        super(detailMessage);
    }

    public CommonException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CommonException(Throwable throwable) {
        super(throwable);
    }
}
