package org.caller.mhealth.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class DiseaseAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private List<Operation> mList;
    private OnChildClickListener onChildClickListener;
    private RecyclerView recyclerView;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public DiseaseAdapter(Context context, List<Operation> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ret = null;
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_disease, parent, false);
        itemView.setOnClickListener(this);
        ret = new MyViewHolder(itemView);
        return ret;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Operation operation = mList.get(position);
            myViewHolder.mTxtDepartment.setText(operation.getDepartment());
            myViewHolder.mTxtPlace.setText(operation.getPlace());
            myViewHolder.mTxtDescription.setText(operation.getDescription());
            myViewHolder.mTxtCount.setText(Integer.toString(operation.getCount()));
            myViewHolder.mTxtRcount.setText(Integer.toString(operation.getRcount()));
            myViewHolder.mTxtFcount.setText(Integer.toString(operation.getFcount()));
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

    @Override
    public void onClick(View v) {
        if (recyclerView != null && onChildClickListener != null) {
            int position = recyclerView.getChildAdapterPosition(v);
            onChildClickListener.onChildClick(recyclerView, v, position);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTxtDepartment;
        private final TextView mTxtPlace;
        private final ImageView mImg;
        private final TextView mTxtDescription;
        private final TextView mTxtCount;
        private final ImageView mCountImg;
        private final TextView mTxtRcount;
        private final ImageView mRcountImg;
        private final TextView mTxtFcount;
        private final ImageView mFcountImg;

        MyViewHolder(View itemView) {
            super(itemView);
            mTxtDepartment = ((TextView) itemView.findViewById(R.id.disease_department));
            mTxtPlace = ((TextView) itemView.findViewById(R.id.disease_place));
            mImg = ((ImageView) itemView.findViewById(R.id.disease_img));
            mTxtDescription = ((TextView) itemView.findViewById(R.id.disease_description));
            mTxtCount = ((TextView) itemView.findViewById(R.id.disease_count));
            mCountImg = ((ImageView) itemView.findViewById(R.id.disease_count_img));
            mTxtRcount = ((TextView) itemView.findViewById(R.id.disease_rcount));
            mRcountImg = ((ImageView) itemView.findViewById(R.id.disease_rcount_img));
            mTxtFcount = ((TextView) itemView.findViewById(R.id.disease_fcount));
            mFcountImg = ((ImageView) itemView.findViewById(R.id.disease_fcount_img));
        }
    }
    public interface OnChildClickListener {
        void onChildClick(RecyclerView parent, View view, int position);
    }
}
