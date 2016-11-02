package org.caller.mhealth.tools;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by wl on 2016/10/31.
 */
public final class HttpTool {


    public static byte[] getByteResult(String url) {
        byte[] bytes = null;

        if (url != null) {
            HttpURLConnection conn = null;
            try {
                URL u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Accept-Encoding", "gzip");
                conn.connect();

                int code = conn.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {

                    InputStream stream = conn.getInputStream();

                    // Accept-Encoding 代表能够接受服务器传过来的压缩内容
                    String encoding = conn.getContentEncoding();
                    if ("gzip".equals(encoding)) {
                        // 解压缩
                        stream = new GZIPInputStream(stream);
                    }
                    bytes = StreamUtil.readStream(stream);
                    StreamUtil.close(stream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(conn);
            }

        }

        return bytes;
    }


    public static String getStringResult(String url) {
        String ret = null;
        try {
            if (getByteResult(url) != null) {
                ret = new String(getByteResult(url), "UTF-8");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static <T> T getJsonResult(String url,Class<T>tclass) {
        T ret = null;
        try {
            if (getByteResult(url) != null) {
                Gson gson=new Gson();
                String temp=new String(getByteResult(url), "UTF-8");
                T t = gson.fromJson(temp, tclass);
                ret=t;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
