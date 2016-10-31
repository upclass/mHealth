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
public class BookFragment extends BaseFragment {


    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "健康图书";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

}
