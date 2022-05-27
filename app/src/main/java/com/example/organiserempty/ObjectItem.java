package com.example.organiserempty;

public class ObjectItem {
    private String header,desc,time,date;


    public ObjectItem(String header, String desc, String time, String date) {
        this.header=header;
        this.desc=desc;
        this.time=time;
        this.date=date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String header) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String header) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}