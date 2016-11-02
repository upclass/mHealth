package org.caller.mhealth;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.caller.mhealth.entitys.Operation;
import org.caller.mhealth.tools.HttpAPI;
import org.caller.mhealth.tools.HttpTool;

public class OperationDetailActivity extends AppCompatActivity implements Runnable, View.OnClickListener {

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    mOperation = (Operation) msg.obj;

                    break;
            }
        }
    };
    private String mUrl;
    private Operation mOperation;
    private TextView mTxtShort;
    private TextView mTxtMore;
    private ImageView mHideImg;
    private ImageView mShowImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_detail);
        mTxtShort = (TextView) findViewById(R.id.disease_text_short);
        mHideImg = (ImageView) findViewById(R.id.disease_text_hide_img);
        mHideImg.setOnClickListener(this);
        mTxtMore = (TextView) findViewById(R.id.disease_text_more);
        mShowImg = (ImageView) findViewById(R.id.disease_text_show_img);
        mShowImg.setOnClickListener(this);
        mOperation = (Operation) getIntent().getParcelableExtra("data");

        mTxtShort.setText(mOperation.getCaretext());

        //TODO: webview
//        mUrl = HttpAPI.getOperationDetailUrl(Long.toString(operation.getId()));
//        new Thread(this).start();
    }

    @Override
    public void run() {
        Message message = mHandler.obtainMessage(2);
        byte[] data = HttpTool.getByteResult(mUrl);
        Gson gson = new Gson();
        Operation operation = gson.fromJson(new String(data), Operation.class);
        message.obj = operation;
        mHandler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.disease_text_hide_img:
                mTxtShort.setVisibility(View.GONE);
                mHideImg.setVisibility(View.GONE);
                mTxtMore.setVisibility(View.VISIBLE);
                mShowImg.setVisibility(View.VISIBLE);
                if (mOperation != null) {
                    mTxtMore.setText(mOperation.getCaretext());
                }
                break;
            case R.id.disease_text_show_img:
                mTxtMore.setVisibility(View.GONE);
                mShowImg.setVisibility(View.GONE);
                mTxtShort.setVisibility(View.VISIBLE);
                mHideImg.setVisibility(View.VISIBLE);
                if (mOperation != null) {
                    mTxtShort.setText(mOperation.getCaretext());
                }
                break;
        }
    }
}
