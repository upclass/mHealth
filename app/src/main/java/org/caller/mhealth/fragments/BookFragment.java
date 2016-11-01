package org.caller.mhealth.fragments;


import android.os.Bundle;
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



import org.caller.mhealth.entitys.BookClassify;
import org.caller.mhealth.entitys.BookClassifyInfo;
import org.caller.mhealth.tools.HttpTool;

import org.caller.mhealth.R;
import org.caller.mhealth.adapters.BookAdapter;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.entitys.BookList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<BookList> mItems;
    private BookAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;


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
                        mItems.clear();
                        List<BookList> obj = (List<BookList>)msg.obj;
                        for (BookList booklist :
                                obj) {
                            mItems.add(booklist);
                        }
                        mAdapter.notifyDataSetChanged();
                }
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
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

        getBookList();
        return view;
    }

    @Override
    public void onRefresh() {
        getBookList();
    }


    public void getBookList() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<BookList> bookLists = new ArrayList<>();
                BookClassify resultClassify = HttpTool.getJsonResult("http://www.tngou.net/api/book/classify", BookClassify.class);
                List<BookClassifyInfo> tngou = resultClassify.getTngou();
                for (BookClassifyInfo info: tngou
                     ) {
                    int id = info.getId();
                    BookList bookList = HttpTool.getJsonResult("http://www.tngou.net/api/book/list?id=" + id, BookList.class);
                    String name = info.getName();
                    bookList.setType(info.getName());
                    bookList.setId(id);
                    bookLists.add(bookList);
                    Message message = mHandler.obtainMessage();
                    message.obj = bookLists;
                    message.what = 998;
                    message.sendToTarget();

                }
//                    Message message = mHandler.obtainMessage();
//                    message.obj = bookLists;
//                    message.what = 998;
//                    message.sendToTarget();
            }
        });
        thread.start();
    }



}
