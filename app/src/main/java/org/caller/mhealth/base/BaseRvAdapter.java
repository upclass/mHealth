package org.caller.mhealth.base;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseRvAdapter.VH> {
    private List<T> mDataList;

    public BaseRvAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId(viewType), parent, false);
        VH vh = new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null
                        && holder.getLayoutPosition() != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(
                            (ViewGroup) itemView.getParent(),
                            itemView,
                            holder.getAdapterPosition(),
                            itemView.getId());
                }

            }
        });
    }

    public abstract int layoutResId(int viewType);

    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position, long id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public static class VH extends RecyclerView.ViewHolder{
        private SparseArrayCompat<View> mViews;

        public VH(final View itemView) {
            super(itemView);
            mViews = new SparseArrayCompat<>();
        }


        public<T extends View> T get(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }
    }
}
