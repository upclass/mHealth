package org.caller.mhealth.fragments.diseasefragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.caller.mhealth.DiseaseWebActivity;
import org.caller.mhealth.R;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.entitys.DiseaseList;
import org.caller.mhealth.entitys.Operation;
import org.caller.mhealth.tools.HttpAPI;
import org.caller.mhealth.tools.HttpTool;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CareFragment extends BaseFragment implements View.OnClickListener {

    private String mUrl;
    private String mWebUrl;
    private OnUrlGetListener mOnUrlGetListener;
    public CareFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "预防护理";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_care, container, false);
        Operation operation = (Operation) getArguments().getParcelable("data");
        long id = operation.getId();

        init(ret, operation);
        return ret;
    }

    private void init(View view, Operation operation) {
        ImageView showWeb = (ImageView) view.findViewById(R.id.care_showWeb);
        WebView webView = (WebView) view.findViewById(R.id.care_webview);
        showWeb.setOnClickListener(this);
        String causetext = operation.getCausetext();
        String drugtext = operation.getDrugtext();
        String message = causetext + "<p> 药物治疗： </p><br>" + drugtext;
        String html = "<html><head></head><body>" + message + "</body></html>";
        webView.loadData(html, "text/html;charset=UTF-8", null);
    }

    @Subscribe
    public void onWebUrlEvent(String webUrl) {
        mWebUrl = webUrl;
    }
    @Override
    public void onClick(View v) {
        if (mWebUrl != null) {
            Intent intent = new Intent(getContext(), DiseaseWebActivity.class);
            intent.putExtra("url", mWebUrl);
            startActivity(intent);
        }
    }
    public interface OnUrlGetListener {
        void onUrlGet(String url);
    }
}
