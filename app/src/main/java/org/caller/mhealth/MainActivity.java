package org.caller.mhealth;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.CommonFragmentPagerAdapter;
import org.caller.mhealth.fragments.BookFragment;
import org.caller.mhealth.fragments.CookFragment;
import org.caller.mhealth.fragments.DiseaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        initToolbar();
        initViewPageAndTabLayout();
    }

    private void initToolbar() {
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        DrawerLayout mainDrawerLayout = (DrawerLayout) findViewById(R.id.main_draw_layout);
        NavigationView mainNavigationView =
                (NavigationView) findViewById(R.id.main_navigation_view);
        mainNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return false;
                    }
                });
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
}
