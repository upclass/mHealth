package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public class CookClassInfo {
    @SerializedName("status")
    private boolean status;

    @SerializedName("tngou")
    private List<CookClassBean> classList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CookClassBean> getClassList() {
        return classList;
    }

    public void setClassList(List<CookClassBean> classList) {
        this.classList = classList;
    }
}
