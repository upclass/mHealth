package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xsm on 16-10-31.
 */

public class DiseaseList {
    @SerializedName("list")
    private List<Operation> mList;

    public List<Operation> getList() {
        return mList;
    }

    public void setList(List<Operation> list) {
        mList = list;
    }
}