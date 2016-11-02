package org.caller.mhealth;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.caller.mhealth.activities.LoginActivity;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.CommonFragmentPagerAdapter;
import org.caller.mhealth.fragments.BookFragment;
import org.caller.mhealth.fragments.CookFragment;
import org.caller.mhealth.fragments.DiseaseFragment;
import org.caller.mhealth.tools.CircleTransform;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mLogin;
    private TextView mUName;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews(savedInstanceState);
        mContext=this;
    }

    private void initViews(Bundle savedInstanceState) {
        initToolbar();
        initViewPageAndTabLayout();
    }

    private void initToolbar() {
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        NavigationView mainNavigationView =
                (NavigationView) findViewById(R.id.main_navigation_view);
        mainNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return false;
                    }
                });
        View view = mainNavigationView.getHeaderView(0);
        mLogin = (ImageView) view.findViewById(R.id.iv_main_header_icon);
        mUName= (TextView) view.findViewById(R.id.tv_account);
        mLogin.setOnClickListener(this);
    }

    private void initViewPageAndTabLayout() {
        ViewPager mainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(adapter());

        TabLayout mainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    private List<BaseFragment> fragments() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new DiseaseFragment());
        fragments.add(new BookFragment());
        fragments.add(new CookFragment());
        return fragments;
    }

    private PagerAdapter adapter() {
        return new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            String result = data.getStringExtra("result");
            if (result != null) {
                showReturnMessage(result);
            }
            String info = data.getStringExtra("info");
            if (info != null) {
                try {
                    Tencent mTencent = Tencent.createInstance("1105695257", this.getApplicationContext());
                    JSONObject jsonObject = new JSONObject(info);
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openID = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(token, expires);
                    UserInfo userInfo = new UserInfo(MainActivity.this, mTencent.getQQToken());
                    IUiListener useInfoListener = new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            JSONObject mInfo = (JSONObject) o;
                            try {
                                String userName=mInfo.getString("nickname");
                                mUName.setText(userName);
                                String userphoto=mInfo.getString("figureurl_qq_1");
                                Glide.with(MainActivity.this).load(userphoto).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .transform(new CircleTransform(MainActivity.this))
                                        .placeholder(R.mipmap.ic_launcher)
                                        .crossFade()
                                        .into(mLogin);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(UiError error) {

                        }


                        @Override
                        public void onCancel() {
                        }

                    };
                    userInfo.getUserInfo(useInfoListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void showReturnMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle("提示")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}