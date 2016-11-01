package org.caller.mhealth.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.caller.mhealth.R;
import org.caller.mhealth.base.BaseRvAdapter;
import org.caller.mhealth.entitys.CookBean;
import org.caller.mhealth.model.CookModel;

import java.util.List;

/**
 * Created by Linked on 2016/11/1 0001.
 */

public class CookListAdapter extends BaseRvAdapter<CookBean> {

    public CookListAdapter(List<CookBean> dataList) {
        super(dataList);
    }

    @Override
    public int layoutResId(int viewType) {
        return R.layout.item_cook;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        super.onBindViewHolder(holder, position);
        CookBean cookBean = getItem(position);

        ImageView ivCookImg = holder.get(R.id.iv_cook_img);
        Glide.with(ivCookImg.getContext())
                .load(CookModel.BASE_IMG_URL + cookBean.getImg())
                .override(300, 200)
                .skipMemoryCache(true)
                .into(ivCookImg);

        TextView tvCookName = holder.get(R.id.tv_cook_name);
        tvCookName.setText(cookBean.getName());

        TextView tvCookFood = holder.get(R.id.tv_cook_food);
        tvCookFood.setText(cookBean.getFood());

        TextView tvCookCount = holder.get(R.id.tv_cook_fcount);
        tvCookCount.setText(cookBean.getFcount() + "收藏");
    }
}
