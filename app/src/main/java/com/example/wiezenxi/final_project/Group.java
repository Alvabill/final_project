package com.example.wiezenxi.final_project;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

import cn.bmob.v3.BmobObject;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class Group extends BmobObject implements Serializable {
    private String group_name;
    private String manager_id;
    private String[] member_id;
    public String getGroup_name() {return group_name;}
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    public String getManager_id() {return manager_id;}
    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }
    public String[] getMember_id() {return member_id;}
    public void setMember_id(String[] member_id) {
        this.member_id = member_id;
    }

}
