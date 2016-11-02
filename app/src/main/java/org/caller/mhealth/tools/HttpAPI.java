package org.caller.mhealth.tools;

//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

/**
 * Created by xsm on 16-10-31.
 */

public class HttpAPI {
    private static final String TAG = "HttpAPI";
    //请求页数，默认page=1 每页返回的条数，默认rows=20
    public static String getListUrl() {
        return getListUrl(1, 20);
    }
    //请求疾病列表，page：请求页数，rows：每页返回的条数
    public static String getListUrl(int page, int rows) {
        String ret = null;
        String baseUrl = "http://www.tngou.net/api/disease/list";
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("?page=" + page + "&rows=" + rows);
        ret = builder.toString();
        return ret;
    }
    public static String getOperationDetailUrl(String id) {
        String ret = null;
        StringBuilder builder = new StringBuilder("http://www.tngou.net/api/disease/show");
        builder.append("?id=" + id);
        ret = builder.toString();
        return ret;
    }
}
