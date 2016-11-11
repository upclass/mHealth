package org.caller.mhealth.activities;

import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.BookList;
import org.caller.mhealth.entitys.CookBean;
import org.caller.mhealth.entitys.CookShowBean;
import org.caller.mhealth.model.CookModel;
import org.caller.mhealth.model.CookModelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CookShowActivity extends AppCompatActivity {

    private static final int NO_ID = -1;
    private ImageView mIvShowCookImg;
    private WebView mWebView;
    private Toolbar mToolbar;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_show);
        initViews(savedInstanceState);
        mId = getIntent().getIntExtra("id", NO_ID);

        if (mId != NO_ID) {
            getFromSD();
        }
    }

    private void fetchCookShow(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CookModel cookModel = new CookModelImpl();
                cookModel.cookShow(id, CookShowBean.class, new CookModel.CallBack<CookShowBean>() {
                    @Override
                    public void onResponse(CookShowBean cookShowBean) {
                        if (cookShowBean != null) {
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                try {
                                    deleteToSDCard("CookShow." + mId + "txt");
                                    saveToSDCard("CookShow." + mId + "txt", cookShowBean);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            updateCookShow(cookShowBean);
                        }
                    }
                });
            }
        }).start();
    }

    public void getFromSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            CookShowBean temp = readFromSD();
            if (temp != null)
                updateCookShow(temp);
            else fetchCookShow(mId);
        } else {
            fetchCookShow(mId);
        }
    }


    private void updateCookShow(CookShowBean cookShowBean) {
        mToolbar.setTitle(cookShowBean.getName());
        Glide.with(this)
                .load(CookModel.BASE_IMG_URL + cookShowBean.getImg())
                .override(300, 200)
                .skipMemoryCache(true)
                .into(mIvShowCookImg);
        mWebView.loadData(
                html(cookShowBean.getMessage()),
                "text/html; charset=UTF-8",
                null);
    }

    private void initViews(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.cook_show_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mIvShowCookImg = (ImageView) findViewById(R.id.iv_cook_show_img);
        mWebView = (WebView) findViewById(R.id.wv_cook_show_message);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.cook_show_fab);
    }


    private String html(String message) {
        return "<html>" +
                "<head>" +
                "<style type=\"text/css\">img {width:100%}\n" +
                "</style>" +
                "</head>" +
                "<body>"
                + message
                + "</body>" +
                "</html>";
    }


    public void saveToSDCard(String filename, CookShowBean datas) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream outStream = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(outStream);
        objOut.writeObject(datas);
        objOut.flush();
        objOut.close();
    }

    public void deleteToSDCard(String filename) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        if (file.exists()) file.delete();
    }

    public CookShowBean readFromSD() {
        CookShowBean ret = null;
        File file = new File(Environment.getExternalStorageDirectory(), "CookShow." + mId + "txt");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            ret = (CookShowBean) objIn.readObject();
            objIn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
