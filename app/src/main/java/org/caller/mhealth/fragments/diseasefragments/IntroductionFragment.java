package org.caller.mhealth.fragments.diseasefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.base.BaseFragment;
import org.caller.mhealth.entitys.Operation;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionFragment extends BaseFragment {

    private static final String TAG = "IntroductionFragment";
    private TextView mTxtShort;
    private ImageView mTxtShortImage;
    private TextView mTxtMore;
    private ImageView mTxtMoreImage;
    private Operation mOperation;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTitle() {
        return "疾病简介";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_introduction, container, false);
        mOperation = (Operation) getArguments().getParcelable("data");

        //name department place description img message
        TextView name = (TextView) ret.findViewById(R.id.disease_detail_name);
        name.setText(mOperation.getName());
        TextView department = (TextView) ret.findViewById(R.id.disease_detail_department);
        department.setText(mOperation.getDepartment());
        TextView description = (TextView) ret.findViewById(R.id.disease_detail_description);
        description.setText("常发区域：" + mOperation.getPlace() + mOperation.getDescription());
        initWebview(ret);
        return ret;
    }

    private void initWebview(View v) {
        WebView webView = (WebView) v.findViewById(R.id.disease_detail_webview);
        String message = mOperation.getMessage();
        String imgUrl = mOperation.getImg();
        String msg = message + "<p> <img src=\"http://tnfs.tngou.net/img" + imgUrl + "\"/></p>" + mOperation.getCausetext() + mOperation.getSymptomtext();
        String html = "<html><head><style>img {width: 100%}</style></head><body>" + msg + "</body></html>";
        Log.d(TAG, "initWebview: " + html);
//        webView.loadDataWithBaseURL("http://tnfs.tngou.net/img", html, "text/html;charset=UTF-8", null, null);
        webView.loadData(html, "text/html;charset=UTF-8", null);
    }

}
