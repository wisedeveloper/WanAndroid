package com.wisedeve.wanandroid.ui.view;

import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BaseMvpView;

/**
 * Description：
 * Created time：18-6-14 下午1:56
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface CollectView extends BaseMvpView {
    void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData);
    void getMoreDataSuccess(ResponseData<ArticleList> articleListResponseData);
    void getDataError(String errorMessage);
}
