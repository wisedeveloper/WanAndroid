package com.wisedeve.wanandroid.ui.view;

import android.support.design.widget.TabLayout;
import android.widget.TableLayout;

import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BaseMvpView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * Description：
 * Created time：18-6-12 下午2:12
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface TypeView extends BaseMvpView {
    TabLayout getTabLayout();
    TagFlowLayout getFlowLayout();
    void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData);
    void getMoreDataSuccess(ResponseData<ArticleList> articleListResponseData);
    void getDataError(String errorMessage);
}
