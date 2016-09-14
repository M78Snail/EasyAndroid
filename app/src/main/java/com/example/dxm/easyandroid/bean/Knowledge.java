package com.example.dxm.easyandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by dxm on 2016/7/8.
 */
public class Knowledge extends BmobObject {
    private String name;
    private String table;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
