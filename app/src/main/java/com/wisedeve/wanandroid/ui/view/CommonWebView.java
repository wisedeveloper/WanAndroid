package com.wisedeve.wanandroid.ui.view;

import android.widget.ProgressBar;

import com.wisedeve.wanandroid.ui.base.BaseMvpView;

/**
 * Description：
 * Created time：18-6-4 下午4:46
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface CommonWebView extends BaseMvpView{
    void setTitle(String title);
    ProgressBar getProgressBar();
}
