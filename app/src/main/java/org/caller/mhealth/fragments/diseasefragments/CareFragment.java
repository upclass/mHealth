package org.caller.mhealth.fragments.diseasefragments;


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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CareFragment extends BaseFragment implements Runnable, View.OnClickListener {

    private String mUrl;
    private String mWebUrl;
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    mWebUrl = (String) msg.obj;
                    break;
            }
        }
    };
    public CareFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "预防护理";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_care, container, false);
        Operation operation = (Operation) getArguments().getParcelable("data");
        long id = operation.getId();
        mUrl = HttpAPI.getOperationDetailUrl(Long.toString(id));
        new Thread(this).start();

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

    @Override
    public void run() {
        if (mUrl != null) {
            byte[] data = HttpTool.getByteResult(mUrl);
            Message message = mHandler.obtainMessage(3);
            try {
                JSONObject obj = new JSONObject(new String(data));
                String webUrl = (String) obj.opt("url");
                if (webUrl != null) {
                    message.obj = webUrl;
                    mHandler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mWebUrl != null) {
            Intent intent = new Intent(getContext(), DiseaseWebActivity.class);
            intent.putExtra("url", mWebUrl);
            startActivity(intent);
        }
    }
}
