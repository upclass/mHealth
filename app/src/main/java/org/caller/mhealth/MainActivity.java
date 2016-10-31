package org.caller.mhealth;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.caller.mhealth.tools.HttpTool;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "test";
    private TextView mClassify;
    private Handler mHandler;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClassify = (TextView) findViewById(R.id.classify_name);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 998:
                        String obj = ((String) msg.obj);
                        mClassify.setText(obj);
                }
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpTool.getStringResult("http://www.tngou.net/api/book/classify");
                Message message = mHandler.obtainMessage();
                message.obj = result;
                message.what = 998;
                message.sendToTarget();
            }
        });
        thread.start();


    }
}
