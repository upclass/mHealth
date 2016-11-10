package org.caller.mhealth.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import org.caller.mhealth.MainActivity;
import org.caller.mhealth.R;
import org.caller.mhealth.adapters.ViewPagerAdapter;
import org.caller.mhealth.tools.DepthPageTransformer;


public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter; // ViewPager适配器
    private int[] images; // 图片资源引用数组（填充引用图片的Id）
    private LinearLayout mIndicator;
    private Button[] buttons;
    private Button btnGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView(); // 初始化视图
        initData(); // 初始化数据
        getFrist();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (LinearLayout) findViewById(R.id.indicator);
        btnGuide = (Button) findViewById(R.id.btn_guide);
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        images = new int[]{R.mipmap.live_guide_01, R.mipmap.live_guide_02};
        mViewPagerAdapter=new ViewPagerAdapter(GuideActivity.this,images);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        initIndicator();
        setCurrentIndicator();


    }

    private void setCurrentIndicator() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Drawable drawable = getResources().getDrawable(R.drawable.live_indicator_on);
                switch (position) {
                    case 0:
                        buttons[0].setBackgroundDrawable(drawable);
                        setDefaultBG(position);
                        break;
                    case 1:
                        buttons[1].setBackgroundDrawable(drawable);
                        setDefaultBG(position);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setDefaultBG(int position) {
        for (int i = 0; i < 2; i++) {
            if (position == 1)
                btnGuide.setVisibility(View.VISIBLE);
            else btnGuide.setVisibility(View.GONE);
            if (i != position) {
                Drawable drawable = getResources().getDrawable(R.drawable.live_indicator_default);
                buttons[i].setBackgroundDrawable(drawable);
            }
        }
    }

    private void initIndicator() {
        Drawable drawable = getResources().getDrawable(R.drawable.live_indicator_default);
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                buttons = new Button[4];
                buttons[i] = new Button(this);
                buttons[i].setBackgroundDrawable(drawable);
                mIndicator.addView(buttons[i]);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50,
                        50);
                buttons[i].setLayoutParams(lp);
            } else {
                buttons[i] = new Button(this);
                buttons[i].setBackgroundDrawable(drawable);
                mIndicator.addView(buttons[i]);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50,
                        50);
                lp.setMargins(25, 0, 0, 0);
                buttons[i].setLayoutParams(lp);
            }
        }
    }

    void getFrist(){
        SharedPreferences mf=getSharedPreferences("myapp", Context.MODE_PRIVATE);
        if (mf.getBoolean("isFrist",true))
            ;
        else {
            SharedPreferences mf1=getSharedPreferences("myuser",Context.MODE_PRIVATE);
            boolean login = mf1.getBoolean("isLogin", false);
            if(login){
                Intent intent=new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
