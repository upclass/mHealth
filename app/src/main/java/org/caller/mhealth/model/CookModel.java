package org.caller.mhealth.model;

import org.caller.mhealth.entitys.CookClassInfo;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public interface CookModel {
    String BASE_IMG_URL = "http://tnfs.tngou.net/img";
    String BASE_URL = "http://www.tngou.net/api/";
    <T> void cookClassInfo(int id, Class<T> classOfT, CallBack<T> f);

    <T> void cookListInfo(int id, int page, int rows, Class<T> classOfT, CallBack<T> f);

    <T> void cookShow(int id, Class<T> classOfT, CallBack<T> f);

    interface CallBack<T> {
        void onResponse(T t);
//        void onErroe(Throwable e);
    }
}
