package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class BookClassify {
    @SerializedName("status")
    private boolean status;
    @SerializedName("tngou")
    private List<BookClassifyInfo>tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BookClassifyInfo> getTngou() {
        return tngou;
    }

    public void setTngou(List<BookClassifyInfo> tngou) {
        this.tngou = tngou;
    }
}
