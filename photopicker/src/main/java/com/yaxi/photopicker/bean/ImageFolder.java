package com.yaxi.photopicker.bean;

/**
 * 图片选择器的列表页，显示文件夹和其中的首图
 * Created by yaxi on 2017/1/6.
 */

public class ImageFolder {
    private String dir;
    private String fitstImagePath;
    private int count;
    private String name;


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        this.name = this.dir.substring(this.dir.lastIndexOf("/"));
    }

    public String getFitstImagePath() {
        return fitstImagePath;
    }

    public void setFitstImagePath(String fitstImagePath) {
        this.fitstImagePath = fitstImagePath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }


}
