package org.caller.mhealth.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import org.caller.mhealth.MainApplication;
import org.caller.mhealth.R;
import org.caller.mhealth.entitys.AddFriendRequest;
import org.caller.mhealth.entitys.MyUser;
import org.caller.mhealth.entitys.RongYunRespon;
import org.caller.mhealth.im.AddFriendItemProvider;

import java.io.IOException;
import java.security.MessageDigest;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener, RongIMClient.OnReceiveMessageListener {
    private static final String TAG = ChattingActivity.class.getSimpleName();
    private Button mBut2, mBut3, mBut4, mBut5, mBut6;
    private ImageView mBackImg;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        getToken();
        RongIM.getInstance().registerMessageType(AddFriendRequest.class);
        RongIM.getInstance().registerMessageTemplate(new AddFriendItemProvider());
        RongIM.setOnReceiveMessageListener(this);
        mBut2 = (Button) findViewById(R.id.bt2);
        mBut3 = (Button) findViewById(R.id.bt3);
        mBut4 = (Button) findViewById(R.id.bt4);
        mBut5 = (Button) findViewById(R.id.bt5);
        mBut6 = (Button) findViewById(R.id.bt6);
        mBackImg = (ImageView) findViewById(R.id.img1);
        mTitle = (TextView) findViewById(R.id.txt1);

//        mBackImg.setVisibility(View.GONE);
        mTitle.setText("主页面");
//        mBut6.setVisibility(View.GONE);

        mBut2.setOnClickListener(this);
        mBut3.setOnClickListener(this);
        mBut4.setOnClickListener(this);
        mBut5.setOnClickListener(this);
        mBut6.setOnClickListener(this);
//        Intent intent=new Intent(this,ConversationListActivity.class);
//        startActivity(intent);
//        RongIM.getInstance().startPrivateChat(this, "2", "标题");
    }
//    void  sendMessageListen(){
//        if (RongIM.getInstance() != null) {
//            //设置自己发出的消息监听器。
//            RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
//        }
//    }
//    void receieveMessageListen(){
//        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt2:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(ChattingActivity.this, "2", "title");
                break;
            case R.id.bt3:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(ChattingActivity.this);
                break;
            case R.id.bt4:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startSubConversationList(ChattingActivity.this, Conversation.ConversationType.GROUP);
                break;
            case R.id.bt5:
                startActivity(new Intent(ChattingActivity.this, ContactsActivity.class));
                break;
            case R.id.bt6:
                Intent intent=new Intent(ChattingActivity.this,SearchUserActivity.class);
                startActivity(intent);
        }
    }




    private  void getToken() {
        OkHttpClient okHttpClient = new OkHttpClient();
        MyUser user = ((MainApplication) getApplicationContext()).getLoginUser();
        String nonce = String.valueOf(Math.random() * 0xffffff);
        String time_stamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = toHex(toSHA1("FPahkvzf7S" + nonce + time_stamp));
        final FormBody requestBody = new FormBody.Builder()
                .add("userId", user.getObjectId())
                .add("name", user.getUsername())
                .add("portraitUri", "f99fc361-13f3-4a0f-89ca-c8485b4c5f29.png")
                .build();
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://api.cn.ronghub.com/user/getToken.json")
                .addHeader("App-Key", "pkfcgjstpwrb8")
                .addHeader("Nonce", nonce)
                .addHeader("Timestamp", time_stamp)
                .addHeader("Signature", signature)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        //发送请求获取响应
            okHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    String s = e.toString();
                    Log.i("test", "onFailure: "+s);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.code()==200){
                        Gson gson =new Gson();
                        String message1 = response.body().string();
                        Log.i("message", "onResponse: "+message1);
                        RongYunRespon json= gson.fromJson(message1, RongYunRespon.class);
                        connect(json.getToken());
                        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("HEALTH_TOKEN", json.getToken());
                        edit.commit();
                    }
                }
            });

    }

    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MainApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("ChattingActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("ChattingActivity:succes",userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("ChattingActivity", "--onError" + errorCode);
                }
            });
        }
    }

    private static byte[] toSHA1(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes());
            return md.digest();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String toHex(byte[] data) {
        final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(0xf0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0f & data[i]];
        }
        return String.valueOf(out);
    }

    @Override
    public boolean onReceived(Message message, int i) {
        MessageContent messageContent = message.getContent();

        if (messageContent instanceof AddFriendRequest) {//文本消息
            AddFriendRequest textMessage = (AddFriendRequest) messageContent;
            Log.d(TAG, "onReceived-TextMessage:" + textMessage.getContent());
        } else {
            Log.d(TAG, "onReceived-其他消息，自己来判断处理");
        }

        return false;
    }

//    private class MySendMessageListener implements RongIM.OnSendMessageListener {
//
//        private static final String TAG = "MySendMessage";
//
//        /**
//         * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
//         *
//         * @param message 发送的消息实例。
//         * @return 处理后的消息实例。
//         */
//        @Override
//        public Message onSend(Message message) {
//            //开发者根据自己需求自行处理逻辑
//            return message;
//        }
//
//        /**
//         * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
//         *
//         * @param message              消息实例。
//         * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
//         * @return true 表示走自已的处理方式，false 走融云默认处理方式。
//         */
//        @Override
//        public boolean onSent(Message message,RongIM.SentMessageErrorCode sentMessageErrorCode) {
//
//            if(message.getSentStatus()== Message.SentStatus.FAILED){
//                if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_CHATROOM){
//                    //不在聊天室
//                }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION){
//                    //不在讨论组
//                }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_GROUP){
//                    //不在群组
//                }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST){
//                    //你在他的黑名单中
//                }
//            }
//
//            MessageContent messageContent = message.getContent();
//
//            if (messageContent instanceof TextMessage) {//文本消息
//                TextMessage textMessage = (TextMessage) messageContent;
//                Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());
//            } else if (messageContent instanceof ImageMessage) {//图片消息
//                ImageMessage imageMessage = (ImageMessage) messageContent;
//                Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
//            } else if (messageContent instanceof VoiceMessage) {//语音消息
//                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
//                Log.d(TAG, "onSent-voiceMessage:" + voiceMessage.getUri().toString());
//            } else if (messageContent instanceof RichContentMessage) {//图文消息
//                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
//                Log.d(TAG, "onSent-RichContentMessage:" + richContentMessage.getContent());
//            } else {
//                Log.d(TAG, "onSent-其他消息，自己来判断处理");
//            }
//
//            return false;
//        }
//    }
//
//    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
//
//        /**
//         * 收到消息的处理。
//         *
//         * @param message 收到的消息实体。
//         * @param left    剩余未拉取消息数目。
//         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
//         */
//        @Override
//        public boolean onReceived(Message message, int left) {
//            //开发者根据自己需求自行处理
//            return false;
//        }
//    }
}
