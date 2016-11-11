package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/1.
 */
public class BookInfo implements Serializable{
    @SerializedName("book")
    private long book;
    @SerializedName("id")
    private long id;
    @SerializedName("message")
    private String message;
    @SerializedName("seq")
    private  long seq;
    @SerializedName("title")
    private String title;
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "\"BookInfo\":{" +
                "\"book\":" + book +
                ", \"id\":" + id +
                ", \"message\":" +"\""+ message +"\""+
                ", \"seq\":" + seq +
                ", \"title\":" + "\""+title +"\""+
                "}";
    }
}
