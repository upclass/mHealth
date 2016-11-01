package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public class CookClassBean {
    @SerializedName("id")
    private int id;
    @SerializedName("cookclass")
    private int cookClass;//上级分类id
    @SerializedName("name")
    private String name;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("keywords")
    private String keywords;
    @SerializedName("seq")
    private int seq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCookClass() {
        return cookClass;
    }

    public void setCookClass(int cookClass) {
        this.cookClass = cookClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
