package org.caller.mhealth.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import org.caller.mhealth.entitys.CookBean;
import org.caller.mhealth.entitys.CookInfo;
import org.caller.mhealth.model.CookModel;
import org.caller.mhealth.model.CookModelImpl;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCookList();
    }

    private void updateCookList() {
        if (!mSrlCookList.isRefreshing()) {
            mSrlCookList.setRefreshing(true);
        }
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
                        List<CookBean> cookList = cookInfo.getCookList();
                        mCookList.clear();
                        mCookList.addAll(cookList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
