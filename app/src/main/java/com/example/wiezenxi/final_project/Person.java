package com.example.wiezenxi.final_project;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class Person extends BmobObject implements Serializable {
    private String id;
    private String password;

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}