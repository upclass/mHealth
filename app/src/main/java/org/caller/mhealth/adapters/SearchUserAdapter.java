package org.caller.mhealth.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import org.caller.mhealth.R;
import org.caller.mhealth.entitys.AddFriendRequest;
import org.caller.mhealth.entitys.MyUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * @author :smile
 * @project:SearchUserAdapter
 * @date :2016-01-22-14:18
 */
public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>{
    private List<MyUser> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public SearchUserAdapter(Context context, List<MyUser> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bindView(mDatas.get(position));
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.serchfriend_item,parent, false);
        MyViewHolder holder= new MyViewHolder(view,mContext);
        return holder;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "MyViewHolder";
        ImageView mi;
        TextView tv;
        Button mbtn;
        Context mContext;
        public MyViewHolder(View view,Context mContext) {
            super(view);
            this.mContext=mContext;
            mi=(ImageView) view.findViewById(R.id.user_photo);
            tv= (TextView) view.findViewById(R.id.name);
            mbtn= (Button) view.findViewById(R.id.go);
        }
        void bindView(final MyUser user){
            Picasso.with(mContext).load(user.getPhoto()).into(mi);
            tv.setText(user.getUsername());
            String info="加我啊";
            final AddFriendRequest message=new AddFriendRequest(info.getBytes());
            mbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 2016/11/12 加好友
                    RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, user.getObjectId(),
                            message, null, null, new RongIMClient.SendMessageCallback() {

                                @Override
                                public void onSuccess(Integer integer) {
                                    Log.d(TAG, "发送成功");
                                }

                                @Override
                                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                                    Log.d(TAG, "发送失败");
                                }
                            }, null);

                }
            });
        }

    }
}