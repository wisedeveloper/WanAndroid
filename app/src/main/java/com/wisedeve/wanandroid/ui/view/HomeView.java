package com.wisedeve.wanandroid.ui.view;

import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.BannerBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BaseMvpView;

import java.util.List;

/**
 * Description：
 * Created time：18-6-3 下午4:06
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface HomeView extends BaseMvpView {
    void showRefreshView(Boolean refresh);
    void getBannerDataSuccess(ResponseData<List<BannerBean>> bannerBeanResponseData);
    void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData);
    void getMoreDataSuccess(ResponseData<ArticleList> articleListResponseData);
    void getDataError(String errorMessage);
}
