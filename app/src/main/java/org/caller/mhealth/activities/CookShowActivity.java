package org.caller.mhealth.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.CookShowBean;
import org.caller.mhealth.model.CookModel;
import org.caller.mhealth.model.CookModelImpl;

public class CookShowActivity extends AppCompatActivity {

    private static final int NO_ID = -1;
    private ImageView mIvShowCookImg;
    private WebView mWebView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_show);
        initViews(savedInstanceState);
        int id = getIntent().getIntExtra("id", NO_ID);
        if (id != NO_ID) {
            fetchCookShow(id);
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
                        updateCookShow(cookShowBean);
                    }
                });
            }
        }).start();
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
}
