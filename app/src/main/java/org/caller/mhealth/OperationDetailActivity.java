package org.caller.mhealth;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.CommonFragmentPagerAdapter;
import org.caller.mhealth.entitys.Operation;
import org.caller.mhealth.fragments.diseasefragments.CareFragment;
import org.caller.mhealth.fragments.diseasefragments.IntroductionFragment;
import org.caller.mhealth.tools.HttpAPI;
import org.caller.mhealth.tools.HttpTool;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class OperationDetailActivity extends AppCompatActivity implements Runnable {

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    mWebUrl = (String) msg.obj;
                    EventBus.getDefault().post(mWebUrl);
                    break;
            }
        }
    };
    private String mUrl;
    private String mWebUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.disease_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //不起作用
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("123456", "onClick: " + v.getId());
//            }
//        });

        //初始化ShareSDK
//        ShareSDK.initSDK(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.disease_detail_tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.disease_detail_viewpager);

        Operation operation = (Operation) getIntent().getParcelableExtra("data");
        long id = operation.getId();
        mUrl = HttpAPI.getOperationDetailUrl(Long.toString(id));
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", operation);
        ArrayList<BaseFragment> list = new ArrayList<>();
        IntroductionFragment introductionFragment = new IntroductionFragment();
        introductionFragment.setArguments(bundle);
        list.add(introductionFragment);

        CareFragment careFragment = new CareFragment();
        careFragment.setArguments(bundle);
        list.add(careFragment);
        CommonFragmentPagerAdapter pagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        new Thread(this).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_disease_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_disease_detail_share:
                showShare();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        oks.setText("mhealth 时刻关注您的健康");
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
    @Override
    public void run() {
        if (mUrl != null) {
            byte[] data = HttpTool.getByteResult(mUrl);
            Message message = mHandler.obtainMessage(3);
            try {
                JSONObject obj = new JSONObject(new String(data));
                String webUrl = (String) obj.opt("url");
                if (webUrl != null) {
                    message.obj = webUrl;
                    mHandler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
