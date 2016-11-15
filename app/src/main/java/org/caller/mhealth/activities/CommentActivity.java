package org.caller.mhealth.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.caller.mhealth.R;
import org.caller.mhealth.adapters.CommentAdapter;
import org.caller.mhealth.entitys.Comment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommentActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private Toolbar mToolbar;
    private String mAddress;
    private ListView mListView;
    private List<Comment>mComments;
    private CommentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initToolbar();
        initListView();
        Intent intent = getIntent();
        mAddress = intent.getStringExtra("address");
    }

    private void initListView() {
        mListView= (ListView) findViewById(R.id.comment_list);
        mComments=new ArrayList<>();
        mAdapter=new CommentAdapter(this,mComments);
        mListView.setAdapter(mAdapter);

    }

    private void initToolbar() {
        mToolbar= (Toolbar) findViewById(R.id.comment_toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);//导航图标
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(this);//menu的点击事件，这句话要写在setSupportActionBar的后面
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_push:
                Intent intent=new Intent(CommentActivity.this,PushCommentActivity.class);
                intent.putExtra("address",mAddress);
                startActivity(intent);
        }
        return true;
    }
    public void  getComments(){
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//        query.addWhereEqualTo("playerName", "比目");
//        query.setLimit(50);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if(e==null){
                    for (Comment comment : object) {
                        mComments.add(comment);
                    }
                    mAdapter.notifyDataSetChanged();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
