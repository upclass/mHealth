package org.caller.mhealth.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.caller.mhealth.entitys.Book;
import org.caller.mhealth.entitys.BookClassify;
import org.caller.mhealth.entitys.BookClassifyInfo;
import org.caller.mhealth.tools.HttpTool;

import org.caller.mhealth.R;
import org.caller.mhealth.adapters.BookAdapter;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.entitys.BookList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.helper.GsonUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<BookList> mItems;
    private BookAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;
    private TextView mTextView;


    @Override
    public String getTitle() {
        return "健康图书";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();
        mAdapter = new BookAdapter(getContext(), mItems);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 998:
                        mSwipeRefreshLayout.setRefreshing(false);
                        List<BookList> obj1 = (List<BookList>) msg.obj;
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            try {
                                deleteToSDCard("myBooklist.txt");
                                saveToSDCard("myBooklist.txt", obj1);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mItems.clear();
                        List<BookList> obj = obj1;
                        for (BookList booklist :
                                obj) {
                            mItems.add(booklist);
                        }
                        mAdapter.notifyDataSetChanged();
                        mTextView.setVisibility(View.GONE);
                        break;
                    case 999:
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                }
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        mTextView = (TextView) view.findViewById(R.id.book_main_loading);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        if (recyclerView != null) {
            //布局管理器，能够对Item进行排版
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(mAdapter);
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recommend_refresh_layout);

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        getBookListFromSD();
        mTextView.setVisibility(View.GONE);
        return view;
    }


    @Override
    public void onRefresh() {
        getBookList();
    }


    public void getBookListFromSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            List<BookList> temp = readFromSD();
            if (temp.size() == 0) {
                getBookList();
            } else {
                mItems.clear();
                for (BookList booklist :
                        temp) {
                    mItems.add(booklist);
                }
                mAdapter.notifyDataSetChanged();
            }
        } else {
            getBookList();
        }
    }


    public void getBookList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<BookList> bookLists = new ArrayList<>();
                BookClassify resultClassify = HttpTool.getJsonResult("http://www.tngou.net/api/book/classify", BookClassify.class);
                if (resultClassify != null) {
                    List<BookClassifyInfo> tngou = resultClassify.getTngou();
                    for (BookClassifyInfo info : tngou
                            ) {
                        int id = info.getId();
                        BookList bookList = HttpTool.getJsonResult("http://www.tngou.net/api/book/list?id=" + id, BookList.class);
                        if (bookList != null) {
                            bookList.setType(info.getName());
                            bookList.setId(id);
                            bookLists.add(bookList);
                            Message message = mHandler.obtainMessage();
                            message.obj = bookLists;
                            message.what = 998;
                            message.sendToTarget();
                        }
                    }
                }else {
                    Message message = mHandler.obtainMessage();
                    message.obj = bookLists;
                    message.what = 999;
                    message.sendToTarget();
                }
            }
        });
        thread.start();
    }

    public void saveToSDCard(String filename, List<BookList> datas) throws Exception {
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

    public List<BookList> readFromSD() {
        List<BookList> ret = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(), "myBooklist.txt");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            ret = ((List<BookList>) objIn.readObject());
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


}
