package org.caller.mhealth.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import org.caller.mhealth.R;
import org.caller.mhealth.adapters.BookInfoAdapter;
import org.caller.mhealth.entitys.Book;
import org.caller.mhealth.tools.HttpTool;

public class BookInfoActivity extends AppCompatActivity {
    private Handler mhandle;
    private long id;
    private TextView mBookTitle;
    private TextView mBookIntroduce;
    private ListView mListView;
    private BookInfoAdapter mAdapter;
    private Book book;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        mListView= (ListView) findViewById(R.id.book_info_list);
        mBookTitle= (TextView) findViewById(R.id.book_name);
        mBookIntroduce= (TextView) findViewById(R.id.book_info_introduce);
        mTextView= (TextView) findViewById(R.id.bookinfo_loading);
        mBookIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBookIntroduce.getEllipsize()!=null){
                    mBookIntroduce.setEllipsize(null);
                    mBookIntroduce.setMaxLines(30);
                }
                else {
                    mBookIntroduce.setEllipsize(TextUtils.TruncateAt.END);
                    mBookIntroduce.setMaxLines(2);
                }

            }
        });

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 1);
        book=new Book();
        mAdapter=new BookInfoAdapter(book,this);
        mListView.setAdapter(mAdapter);


        mhandle=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 998:
                        Book obj = ((Book) msg.obj);
                        book.setList(obj.getList());
                        mBookTitle.setText(obj.getName());
                        mBookIntroduce.setText(obj.getSummary());
                        mAdapter.notifyDataSetChanged();
                        mTextView.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Book result = HttpTool.getJsonResult("http://www.tngou.net/api/book/show?id=" + id, Book.class);
                Log.d("test", "run: "+result);
                Message message = mhandle.obtainMessage();
                message.what=998;
                message.obj=result;
                mhandle.sendMessage(message);
            }
        });
        thread.start();
    }
}
