package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wl on 2016/10/31.
 */

import java.util.List;

public class BookList {
    @SerializedName("list")
    private List<Book> list;
    @SerializedName("page")
    private int page;
    @SerializedName("size")
    private int size;
    @SerializedName("status")
    private boolean status;
    @SerializedName("total")
    private  long total;
    @SerializedName("totalpage")
    private int totalpage;
    private String type;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}
