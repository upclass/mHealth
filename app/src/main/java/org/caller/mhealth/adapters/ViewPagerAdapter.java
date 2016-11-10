package org.caller.mhealth.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] images; // 图片资源引用数组

    public ViewPagerAdapter(Context context, int[] datas){
        images = datas;
        mContext = context;
    }

    /**
     * 获取数据（图片）数量
     * @return
     */
    @Override
    public int getCount() {
        return images.length;
    }

    /**
     * 当前View是否是instantiateItem中返回的对象
     * @param view 当前view
     * @param object 由instantiateItem（）方法返回的对象
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化Item并向页面添加
     * @param container ViewPager
     * @param position 要实例化item对应的页面位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView = createImageView(mContext, position);
        container.addView(mImageView);
        return mImageView;
    }

    /**
     * 移除ViewPager中的Item
     * @param container viewpager
     * @param position 要移除页面的位置
     * @param object 要移除页面的对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    /**
     * 创建ImageView
     * @param mContext
     * @param position
     * @return imageView
     */
    private ImageView createImageView(Context mContext, int position) {
        ImageView imageview = new ImageView(mContext);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams(); // 等价于ViewGroup.LayoutParams(FILL_PARENT, FILL_PARENT)
        imageview.setLayoutParams(layoutParams); // 设置ImageView充满父容器
        imageview.setImageResource(images[position]); // 设置ImageView的显示图片
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP); // 设置ImageView缩放样式
        return imageview;
    }
}
