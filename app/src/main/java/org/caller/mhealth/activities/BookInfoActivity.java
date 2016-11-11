package org.caller.mhealth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.caller.mhealth.R;
import org.caller.mhealth.adapters.BookInfoAdapter;
import org.caller.mhealth.entitys.Book;
import org.caller.mhealth.entitys.BookList;
import org.caller.mhealth.tools.HttpTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
        mListView = (ListView) findViewById(R.id.book_info_list);
        mBookTitle = (TextView) findViewById(R.id.book_name);
        mBookIntroduce = (TextView) findViewById(R.id.book_info_introduce);
        mTextView = (TextView) findViewById(R.id.bookinfo_loading);
        mBookIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBookIntroduce.getEllipsize() != null) {
                    mBookIntroduce.setEllipsize(null);
                    mBookIntroduce.setMaxLines(30);
                } else {
                    mBookIntroduce.setEllipsize(TextUtils.TruncateAt.END);
                    mBookIntroduce.setMaxLines(2);
                }

            }
        });

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 1);
        book = new Book();
        mAdapter = new BookInfoAdapter(book, this);
        mListView.setAdapter(mAdapter);


        mhandle = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 998:
                        Book obj = ((Book) msg.obj);
                        if (obj != null) {
                            book.setList(obj.getList());
                            mBookTitle.setText(obj.getName());
                            mBookIntroduce.setText(obj.getSummary());
                            mAdapter.notifyDataSetChanged();
                            mTextView.setVisibility(View.GONE);
                        }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Book book = readInfoFromSD();
            if (book == null) {
                getFromServer();
            } else {
                Message message = mhandle.obtainMessage();
                message.what = 998;
                message.obj = book;
                mhandle.sendMessage(message);
            }
        } else {
            getFromServer();
        }
    }

    private void getFromServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Book result = HttpTool.getJsonResult("http://www.tngou.net/api/book/show?id=" + id, Book.class);
                Log.d("test", "run: " + result);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        if (result != null) {
                            deleteToSDCard("Book" + id + ".txt");
                            saveToSDCard("Book" + id + ".txt", result);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Message message = mhandle.obtainMessage();
                message.what = 998;
                message.obj = result;
                mhandle.sendMessage(message);
            }
        });
        thread.start();
    }

    public Book readInfoFromSD() {

        Book ret = null;
        File file = new File(Environment.getExternalStorageDirectory(), "Book" +id+".txt");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            ret = ((Book) objIn.readObject());
            objIn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;

    }


    public void saveToSDCard(String filename, Book datas) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream outStream = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(outStream);
        objOut.writeObject(datas);
        objOut.flush();
        objOut.close();
    }

    public void deleteToSDCard(String filename) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        if (file.exists()) file.delete();
    }
}
