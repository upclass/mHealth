package org.caller.mhealth.im;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.AddFriendRequest;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.util.AndroidEmoji;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by Administrator on 2016/11/12.
 */

@ProviderTag(messageContent = AddFriendRequest.class)
public class AddFriendItemProvider extends IContainerItemProvider.MessageProvider<AddFriendRequest> {

    @Override
    public void bindView(View view, int i, AddFriendRequest request, UIMessage message) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.message.setText(request.getContent());
        AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }


    @Override
    public void onItemClick(View view, int i, AddFriendRequest request, UIMessage message) {

    }

    @Override
    public void onItemLongClick(View view, int i, AddFriendRequest request, UIMessage message) {

    }

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_item, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(android.R.id.text1);
        view.setTag(holder);
        return view;
    }


    @Override
    public Spannable getContentSummary(AddFriendRequest data) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }


}

