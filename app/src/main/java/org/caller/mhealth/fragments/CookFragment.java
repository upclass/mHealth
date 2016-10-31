package org.caller.mhealth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.caller.mhealth.R;
import org.caller.mhealth.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookFragment extends BaseFragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook, container, false);
    }

}
