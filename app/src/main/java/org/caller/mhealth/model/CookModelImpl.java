package org.caller.mhealth.model;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.caller.mhealth.entitys.CookClassInfo;
import org.caller.mhealth.tools.HttpTool;

import java.util.Locale;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public class CookModelImpl implements CookModel {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public <T> void cookClassInfo(int id, Class<T> classOfT, final CallBack<T> f) {
        String url = CookModel.BASE_URL + "cook/classify?id=" + id;
        beanFromUrl(url, classOfT, f);
    }

    @Override
    public <T> void cookListInfo(int id, int page, int rows, Class<T> classOfT, CallBack<T> f) {
        String url = CookModel.BASE_URL +
                String.format(
                        Locale.CHINA,
                        "cook/list?id=%d&page=%d&rows=%d",
                        id, page, rows);
        beanFromUrl(url, classOfT, f);
    }

    @Override
    public <T> void cookShow(int id, Class<T> classOfT, CallBack<T> f) {
        String url = CookModel.BASE_URL + "cook/show?id=" + id;
        beanFromUrl(url, classOfT, f);
    }

    private <T> void beanFromUrl(String url, Class<T> classOfT, final CallBack<T> f) {
        String json = HttpTool.getStringResult(url);
        Gson gson = new Gson();
        final T t = gson.fromJson(json, classOfT);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                f.onResponse(t);
            }
        });
    }
}
