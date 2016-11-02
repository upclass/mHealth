package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public class CookInfo {
    @SerializedName("status")
    private boolean status;
    @SerializedName("total")
    private int total;
    @SerializedName("tngou")
    private List<CookBean> cookList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CookBean> getCookList() {
        return cookList;
    }

    public void setCookList(List<CookBean> cookList) {
        this.cookList = cookList;
    }
}
