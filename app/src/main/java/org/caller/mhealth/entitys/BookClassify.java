package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wl on 2016/10/31.
 */

public class BookClassify {
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("keywords")
    private String keywords;
    @SerializedName("name")
    private String name;
    @SerializedName("seq")
    private int seq;
    @SerializedName("title")
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
