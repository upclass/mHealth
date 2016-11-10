package org.caller.mhealth.tools;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2016/11/4.
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75F; // 最小缩放比例

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // [负无穷，-1）:当前页面已经滑出左边屏幕，我们已经看不到了
            page.setAlpha(0F);
        } else if (position <= 0) { // [-1, 0]：当前页面向左画出，已远离中心位置，但还未滑出左屏幕
            page.setAlpha(1F);
            page.setTranslationX(0F);
            page.setScaleX(1F);
            page.setScaleY(1F);
        } else if (position <= 1) { // (0,1]:下一页面已经进入屏幕，但还在进入并未到达中间位置
            page.setAlpha(1 - position);
            page.setTranslationX(page.getWidth() * -position);
            float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
            page.setScaleX(scale);
            page.setScaleY(scale);
        } else { // (1, 正无穷]：下一页面还未进入屏幕
            page.setAlpha(0F);
        }
    }
}
