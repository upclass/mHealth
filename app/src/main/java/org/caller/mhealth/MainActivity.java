package org.caller.mhealth;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.caller.mhealth.activities.ActivityAbout;
import org.caller.mhealth.activities.ChattingActivity;
import org.caller.mhealth.activities.CommentActivity;
import org.caller.mhealth.activities.GetUserPhotoActivity;
import org.caller.mhealth.activities.LocationActivity;
import org.caller.mhealth.activities.LoginActivity;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.CommonFragmentPagerAdapter;
import org.caller.mhealth.entitys.MyUser;
import org.caller.mhealth.fragments.BookFragment;
import org.caller.mhealth.fragments.CookFragment;
import org.caller.mhealth.widgets.CircleImg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImg mLogin;
    private Context mContext;
    private View mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFrist();
        setCurrentUser();
        initViews(savedInstanceState);
        mContext = this;
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
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.action_about:
                                Intent intent = new Intent(MainActivity.this, ActivityAbout.class);
                                startActivity(intent);
                                break;
                            case R.id.action_sport:
                                Intent intent1 = new Intent(MainActivity.this, LocationActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.action_loginout:
                                SharedPreferences mf = getSharedPreferences("myuser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mf.edit();
                                editor.putBoolean("isLogin", false);
                                editor.commit();
                                BmobUser.logOut();   //清除缓存用户对象
                                ((MainApplication) getApplicationContext()).loginUser = new MyUser();
                                finish();
                                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.action_chat:
                                Intent intent2 = new Intent(MainActivity.this, ChattingActivity.class);
                                startActivity(intent2);
                                break;
//                            case R.id.action_comment:
//                                Intent intent4 = new Intent(MainActivity.this, CommentActivity.class);
//                                startActivity(intent4);
//                                break;
                        }
                        return false;
                    }
                });
        mView = mainNavigationView.getHeaderView(0);
        mLogin = (CircleImg) mView.findViewById(R.id.iv_main_header_icon);
        mLogin.setOnClickListener(this);
        MyUser user = ((MainApplication) getApplication()).getLoginUser();
        Picasso.with(MainActivity.this).load(user.getPhoto()).into(mLogin);

    }

    private void initViewPageAndTabLayout() {
        ViewPager mainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(adapter());

        TabLayout mainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    private List<BaseFragment> fragments() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new BookFragment());
        fragments.add(new CookFragment());
        return fragments;
    }

    private PagerAdapter adapter() {
        return new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments());
    }

    @Override
    public void onClick(View v) {

        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_dialog, null);
        TextView tv_take = (TextView) view.findViewById(R.id.tv_take);
        TextView tv_select = (TextView) view.findViewById(R.id.tv_select);
        final Dialog mBottomSheetDialog = new Dialog(MainActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        tv_take.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetUserPhotoActivity.class);
                intent.putExtra("isTake", true);
                startActivityForResult(intent, 998);
                mBottomSheetDialog.dismiss();
            }
        });

        tv_select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetUserPhotoActivity.class);
                intent.putExtra("isTake", false);
                startActivityForResult(intent, 999);
                mBottomSheetDialog.dismiss();
            }
        });
    }


    void setFrist() {
        SharedPreferences mf = getSharedPreferences("myapp", Context.MODE_PRIVATE);
        SharedPreferences mf1 = getSharedPreferences("myuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mf.edit();
        SharedPreferences.Editor edit1 = mf1.edit();
        edit.putBoolean("isFrist", false);
        edit1.putBoolean("isLogin", true);
        edit.commit();
        edit1.commit();
    }

    void setCurrentUser() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        ((MainApplication) getApplication()).setLoginUser(userInfo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            String result = data.getStringExtra("result");
            Snackbar.make(mView, result, Snackbar.LENGTH_SHORT)
                    .setAction("提示", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
            String path = data.getStringExtra("path");
            setMyPhoto(path);
//            String url = data.getStringExtra("photo");
//            Picasso.with(MainActivity.this).
//                    load(url).
//                    placeholder(R.mipmap.ic_launcher).
//                    into(mLogin);
        }
    }

    private void setMyPhoto(String path) {
//        final MyUser loginUser = ((MainApplication) getApplication()).getLoginUser();
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    final String url = bmobFile.getFileUrl();
                    MyUser user = ((MainApplication) getApplication()).getLoginUser();
                    MyUser updataUser=new MyUser();
                    updataUser.setPhoto(url);
                    updataUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Picasso.with(MainActivity.this).
                                        load(url).
                                        placeholder(R.mipmap.ic_launcher).
                                        into(mLogin);
                                Snackbar.make(mView,"成功更换头像",Snackbar.LENGTH_LONG);
                            } else {
                                Snackbar.make(mView,"上传头像成功，更新我的信息失败：" + e.getMessage() + "," + e.getErrorCode(),Snackbar.LENGTH_LONG);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "头像上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}