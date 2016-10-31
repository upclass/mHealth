package org.caller.mhealth.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.caller.mhealth.OperationDetailActivity;
import org.caller.mhealth.R;
import org.caller.mhealth.adapters.DiseaseAdapter;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.entitys.DiseaseList;
import org.caller.mhealth.entitys.Operation;
import org.caller.mhealth.tools.HttpAPI;
import org.caller.mhealth.tools.HttpTool;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, Runnable, DiseaseAdapter.OnChildClickListener {


    private RecyclerView mRecyclerView;
    private List<Operation> mList;
    private String mUrl;
    private DiseaseAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    DiseaseList diseaseList = (DiseaseList) msg.obj;
                    mList.clear();
                    mList.addAll(diseaseList.getList());
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private SwipeRefreshLayout mRefreshLayout;

    public DiseaseFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "常见疾病";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_disease, container, false);
        mRecyclerView = (RecyclerView) ret.findViewById(R.id.disease_recycler);
        mList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DiseaseAdapter(getContext(), mList);
        mAdapter.setOnChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.disease_refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mUrl = HttpAPI.getListUrl(1, 20);
        new Thread(this).start();
        return ret;
    }

    @Override
    public void onRefresh() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (mUrl != null) {
            byte[] data = HttpTool.getByteResult(mUrl);
            Gson gson = new Gson();
            DiseaseList diseaseList = gson.fromJson(new String(data), DiseaseList.class);
            Message message = mHandler.obtainMessage(1);
            message.obj = diseaseList;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onChildClick(RecyclerView parent, View view, int position) {
        Operation operation = mList.get(position);
        Intent intent = new Intent(getContext(), OperationDetailActivity.class);
        intent.putExtra("data", operation);
        startActivity(intent);
    }
}
