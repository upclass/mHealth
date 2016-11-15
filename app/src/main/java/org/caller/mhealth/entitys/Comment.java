package org.caller.mhealth.entitys;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/14.
 */

public class Comment extends BmobObject {
    private String name;
    private long time;
    private String content;
    private String photo;
    private String photoAdd;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoAdd() {
        return photoAdd;
    }

    public void setPhotoAdd(String photoAdd) {
        this.photoAdd = photoAdd;
    }
}
