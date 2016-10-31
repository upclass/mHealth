package org.caller.mhealth.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

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
public class DiseaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, Runnable {


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
                    mList = diseaseList.getList();
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

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
        mRecyclerView.setAdapter(mAdapter);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.disease_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
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
}
