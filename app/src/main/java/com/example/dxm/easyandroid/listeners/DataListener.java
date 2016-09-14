package com.example.dxm.easyandroid.listeners;

/**
 * Created by duxiaoming on 16/8/3.
 */
public interface DataListener<T> {
    void complete(T result);
    void fail();
}
