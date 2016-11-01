package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wl on 2016/10/31.
 */

public class Book {
    @SerializedName("author")
    private  String author;
    @SerializedName("bookclass")
    private  int bookclass;
    @SerializedName("count")
    private long count;
    @SerializedName("fcount")
    private long fcount;
    @SerializedName("id")
    private  long id;
    @SerializedName("img")
    private String img;
    @SerializedName("name")
    private String name;
    @SerializedName("rcount")
    private  long rcount;
    @SerializedName("summary")
    private String summary;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRcount() {
        return rcount;
    }

    public void setRcount(long rcount) {
        this.rcount = rcount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getFcount() {
        return fcount;
    }

    public void setFcount(long fcount) {
        this.fcount = fcount;
    }

    public int getBookclass() {
        return bookclass;
    }

    public void setBookclass(int bookclass) {
        this.bookclass = bookclass;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
