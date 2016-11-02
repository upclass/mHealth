package org.caller.mhealth.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.caller.mhealth.R;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Tencent mTencent;
    private BaseUiListener mListener;
    private Button mLoginUserQQ;
    private Intent mIntent;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mIntent = getIntent();
        mLoginUserQQ = (Button) findViewById(R.id.btn_login_qq);
        mLoginUserQQ.setOnClickListener(this);
        setResult(10, mIntent);

    }

    @Override
    public void onClick(View v) {
        showProgresDialog();
        mTencent = Tencent.createInstance("1105695257", this.getApplicationContext());
        mListener = new BaseUiListener();
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "sss", mListener);
        }
    }


    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            returnMessage("success");
            JSONObject json = (JSONObject) response;
            String result = json.toString();
            mIntent.putExtra("info", result);
            finish();
        }


        @Override
        public void onError(UiError e) {
            returnMessage("error");
            finish();
        }

        @Override
        public void onCancel() {
            returnMessage("onCancle");
            finish();
        }
    }


    void returnMessage(String message) {
        mIntent.putExtra("result", message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        }
    }

    public void showProgresDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在加载·············");
        mProgressDialog.show();
    }
}
