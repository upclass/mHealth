package org.caller.mhealth;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.CommonFragmentPagerAdapter;
import org.caller.mhealth.entitys.Operation;
import org.caller.mhealth.fragments.diseasefragments.CareFragment;
import org.caller.mhealth.fragments.diseasefragments.IntroductionFragment;
import org.caller.mhealth.tools.HttpAPI;
import org.caller.mhealth.tools.HttpTool;

import java.util.ArrayList;

public class OperationDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.disease_detail_toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.disease_detail_tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.disease_detail_viewpager);
        setSupportActionBar(toolbar);

        Operation operation = (Operation) getIntent().getParcelableExtra("data");
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
    }




}
