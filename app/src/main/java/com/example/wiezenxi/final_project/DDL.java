package com.example.wiezenxi.final_project;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wiezenxi on 2018/1/2.
 */

public class DDL extends BmobObject implements Serializable,Comparable<DDL> {
    private String id;
    private String title;
    private String content;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {this.month = month;}

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    @Override
    public int compareTo(DDL ddl)
    {
        if(ddl.getYear()<this.getYear())
            return 1;
        if(ddl.getYear()>this.getYear())
            return -1;
        if(ddl.getMonth()<this.getMonth())
            return 1;
        if(ddl.getMonth()>this.getMonth())
            return -1;
        if(ddl.getDay()<this.getDay())
            return 1;
        if(ddl.getDay()>this.getDay())
            return -1;
        if(ddl.getHour()<this.getHour())
            return 1;
        if(ddl.getHour()>this.getHour())
            return -1;
        if(ddl.getMin()<this.getMin())
            return 1;
        if(ddl.getMin()>this.getMin())
            return -1;
        return 0;
    }
}
