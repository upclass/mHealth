package org.caller.mhealth.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.entitys.Operation;

import java.util.List;

/**
 * Created by xsm on 16-10-31.
 */

public class DiseaseAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<Operation> mList;

    public DiseaseAdapter(Context context, List<Operation> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ret = null;
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_disease, parent, false);
        ret = new MyViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Operation operation = mList.get(position);
            myViewHolder.mTxtDepartment.setText(operation.getDepartment());
            myViewHolder.mTxtPlace.setText(operation.getPlace());
            myViewHolder.mTxtDescription.setText(operation.getDescription());
            Glide.with(mContext)
                    .load("http://tnfs.tngou.net/img" + operation.getImg())
                    .into(myViewHolder.mImg);
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTxtDepartment;
        private final TextView mTxtPlace;
        private final ImageView mImg;
        private final TextView mTxtDescription;

        MyViewHolder(View itemView) {
            super(itemView);
            mTxtDepartment = ((TextView) itemView.findViewById(R.id.disease_department));
            mTxtPlace = ((TextView) itemView.findViewById(R.id.disease_place));
            mImg = ((ImageView) itemView.findViewById(R.id.disease_img));
            mTxtDescription = ((TextView) itemView.findViewById(R.id.disease_description));
        }
    }
}
