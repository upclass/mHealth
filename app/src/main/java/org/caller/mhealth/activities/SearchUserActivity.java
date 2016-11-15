package org.caller.mhealth.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.caller.mhealth.R;
import org.caller.mhealth.adapters.SearchUserAdapter;
import org.caller.mhealth.entitys.MyUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchUserActivity extends AppCompatActivity {

    @BindView(R.id.et_find_name)
    EditText et_find_name;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    LinearLayoutManager layoutManager;
    SearchUserAdapter adapter;
    private List<MyUser> mMyUserList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        mMyUserList = new ArrayList<>();
        adapter =new SearchUserAdapter(this, mMyUserList);
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        rc_view.setAdapter(adapter);
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void onSearchClick(){
        query();
    }

    public void query(){
        String name =et_find_name.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", name);
        query.setLimit(50);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                sw_refresh.setRefreshing(false);
                if(e==null){
                    mMyUserList.clear();
                    Toast.makeText(SearchUserActivity.this, "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                    for (MyUser user : object) {
                        mMyUserList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
