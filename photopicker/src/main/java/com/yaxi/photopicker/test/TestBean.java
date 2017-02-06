package com.yaxi.photopicker.test;

/**
 * Created by yaxi on 2017/1/3.
 */

public class TestBean {
    private String des;
    private int imgid;
    private int type;

    public TestBean(int imgid, int type) {
        this.imgid = imgid;
        this.type = type;
    }

    public TestBean(String des, int type) {
        this.des = des;
        this.type = type;
    }

    public TestBean(String des, int imgid, int type) {
        this.des = des;
        this.imgid = imgid;
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
