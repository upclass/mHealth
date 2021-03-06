package org.caller.mhealth.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.caller.mhealth.R;
import org.caller.mhealth.activities.CookShowActivity;
import org.caller.mhealth.adapters.CookListAdapter;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.base.BaseRvAdapter;
import org.caller.mhealth.entitys.BookList;
import org.caller.mhealth.entitys.CookBean;
import org.caller.mhealth.entitys.CookInfo;
import org.caller.mhealth.model.CookModel;
import org.caller.mhealth.model.CookModelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookFragment extends BaseFragment {


    private CookListAdapter mAdapter;
    private ArrayList<CookBean> mCookList;
    private SwipeRefreshLayout mSrlCookList;

    public CookFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "健康菜谱";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cook, container, false);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            List<CookBean> been = readFromSD();
            if (been.size() != 0) {
                mCookList.clear();
                mCookList.addAll(been);
                mAdapter.notifyDataSetChanged();
            }
            else updateCookList();
        }
        else
            updateCookList();
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        initRecyclerView(rootView);
        mSrlCookList = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_cook_list);
        mSrlCookList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCookList();
            }
        });
    }

    private void initRecyclerView(View rootView) {
        RecyclerView rvCookList = (RecyclerView) rootView.findViewById(R.id.rv_cook_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this.getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        rvCookList.setLayoutManager(layoutManager);
        mCookList = new ArrayList<>();
        mAdapter = new CookListAdapter(mCookList);
        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position, long id) {
                toCookShow(mCookList.get(position).getId());
            }
        });
        rvCookList.setAdapter(mAdapter);
    }

    private void toCookShow(int id) {
        Intent intent = new Intent(this.getContext(), CookShowActivity.class);
        intent.putExtra("id", id);
        getContext().startActivity(intent);
    }


    private void updateCookList() {
//        if (!mSrlCookList.isRefreshing()) {
//            mSrlCookList.setRefreshing(true);
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CookModel cookModel = new CookModelImpl();
                cookModel.cookListInfo(0, 1, 20, CookInfo.class, new CookModel.CallBack<CookInfo>() {
                    @Override
                    public void onResponse(CookInfo cookInfo) {
                        if (mSrlCookList.isRefreshing()) {
                            mSrlCookList.setRefreshing(false);
                        }
                        if(cookInfo!=null){
                            List<CookBean> cookList = cookInfo.getCookList();
                            if(cookList!=null&&cookList.size()!=0){
                                if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
                                    try {
                                        deleteToSDCard("CookBean.txt");
                                        saveToSDCard("CookBean.txt",cookList);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                mCookList.clear();
                                mCookList.addAll(cookList);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void saveToSDCard(String filename, List<CookBean> datas) throws Exception {
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

    public List<CookBean> readFromSD() {
        List<CookBean> ret = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(), "CookBean.txt");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            ret = ((List<CookBean>) objIn.readObject());
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
