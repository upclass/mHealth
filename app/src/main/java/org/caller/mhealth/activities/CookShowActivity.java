package org.caller.mhealth.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.CookShowBean;
import org.caller.mhealth.model.CookModel;
import org.caller.mhealth.model.CookModelImpl;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class CookShowActivity extends AppCompatActivity implements View.OnClickListener {

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
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.cook_show_fab);
        floatingActionButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        showShare();
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("mhealth");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("mhealth 时刻关注健康美食");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
