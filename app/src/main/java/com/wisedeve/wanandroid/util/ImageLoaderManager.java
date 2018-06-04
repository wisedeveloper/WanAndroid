package com.wisedeve.wanandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wisedeve.wanandroid.R;

/**
 * Description：
 * Created time：18-6-4 下午2:15
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class ImageLoaderManager {
    public static void LoadImage(Context context, String imgUrl, ImageView imageView) {

        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.default_img)
                .dontAnimate() //解决圆形图显示占位图问题
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
