package org.caller.mhealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.Comment;
import org.caller.mhealth.widgets.CircleImg;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mDatas;

    public CommentAdapter(Context context, List<Comment> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
            convertView = view;
            MyViewHolder holder = new MyViewHolder(view);
            convertView.setTag(holder);
        }
        MyViewHolder tag = (MyViewHolder) convertView.getTag();
        tag.bindView(mContext, mDatas.get(position));

        ret = convertView;
        return ret;
    }

    public static class MyViewHolder {
        private CircleImg mImg;
        private TextView mName;
        private TextView mTime;
        private TextView mContent;
        private ImageView mUpload;

        public MyViewHolder(View view) {
            mImg = (CircleImg) view.findViewById(R.id.myPhoto);
            mName = (TextView) view.findViewById(R.id.my_name);
            mTime = (TextView) view.findViewById(R.id.my_time);
            mContent = (TextView) view.findViewById(R.id.comment_content);
            mUpload = (ImageView) view.findViewById(R.id.up_pic);
        }

        public void bindView(Context context, Comment comment) {
            Picasso.with(context).load(comment.getPhoto()).into(mImg);
            mName.setText(comment.getName());
            mTime.setText(String.valueOf(TranTime(comment.getTime())));
            mContent.setText(comment.getContent());
            Picasso.with(context).load(comment.getPhoto()).into(mUpload);
        }

        public String TranTime(long time) {
            Date date = new Date(time);
            StringBuilder result = new StringBuilder();
            result.append(date.getHours())
                    .append(":")
                    .append(date.getMinutes())
                    .append(":")
                    .append(date.getSeconds());
            return result.toString();
        }
    }


}
