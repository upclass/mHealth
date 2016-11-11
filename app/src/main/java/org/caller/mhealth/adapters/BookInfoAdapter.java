package org.caller.mhealth.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.Book;
import org.caller.mhealth.entitys.BookInfo;


/**
 * Created by Administrator on 2016/11/1.
 */
public class BookInfoAdapter extends BaseAdapter {
    private Context mContext;
    private Book mitem;

    public BookInfoAdapter(Book mitem, Context context) {
        this.mitem = mitem;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mitem.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return mitem.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret;
        if (convertView != null) {
            ret = convertView;
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.healthy_bookinfo_item, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            ret = convertView;
        }

        ViewHolder tag = (ViewHolder) convertView.getTag();
        tag.bindView(this, mitem, position);
        return ret;
    }


    public static class ViewHolder {
        private TextView mTitle;
        private WebView mMessage;

        ViewHolder(View item) {
            mTitle = (TextView) item.findViewById(R.id.bookinfo_message);
            mMessage = (WebView) item.findViewById(R.id.bookinfo_web);
        }

        void bindView(final BookInfoAdapter adapter, final Book item, final int position) {
            mTitle.setText(item.getList().get(position).getTitle());
            mTitle.setTag(item.getList().get(position).getMessage());
            if (item.getList().get(position).isShow()) {
                mMessage.setVisibility(View.VISIBLE);
                mTitle.setTextColor(Color.rgb(255, 0, 0));
            } else {
                mMessage.setVisibility(View.GONE);
                mTitle.setTextColor(Color.rgb(0, 0, 0));
            }
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isShow = item.getList().get(position).isShow();
                    TextView v1 = (TextView) v;
//                    v1.setTextColor(Color.rgb(255, 0, 0));
                    WebView mWebView = mMessage;
                    String tag = ((String) v.getTag());
                    String html = "<html><head><style>img {width: 100%}</style></head><body>" + tag + "</body></html>";
                    mWebView.loadData(html, "text/html;charset=UTF-8", null);
                    if (!isShow) {
                        v1.setTextColor(Color.rgb(255, 0, 0));
                        mWebView.setVisibility(View.VISIBLE);
                        item.getList().get(position).setShow(true);
                        for (int i = 0; i < item.getList().size(); i++) {
                            if (i != position)
                                item.getList().get(i).setShow(false);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        mWebView.setVisibility(View.GONE);
                        v1.setTextColor(Color.rgb(0, 0, 0));
                        item.getList().get(position).setShow(false);
                    }
                }
            });
        }
    }
}
