package org.caller.mhealth.entitys;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/12.
 */

public class RongYunRespon implements Serializable{
    @SerializedName("code")
    private int code;
    @SerializedName("userId")
    private  String userId;
    @SerializedName("token")
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
