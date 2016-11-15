package org.caller.mhealth;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.caller.mhealth.activities.LoginActivity;
import org.caller.mhealth.entitys.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2016/11/10.
 */

public class MainApplication extends Application {
    public MyUser loginUser;

    public MyUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(MyUser loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBmobSDK();
        RongIM.init(this);
    }

    private void initBmobSDK() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("0f05733c0ba69d0ff75b31e1f7cc7010")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
