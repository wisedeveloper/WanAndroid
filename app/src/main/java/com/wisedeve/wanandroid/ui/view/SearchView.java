package com.wisedeve.wanandroid.ui.view;

import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.HotKeyBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BaseMvpView;

import java.util.List;

/**
 * Description：
 * Created time：18-6-11 下午2:06
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface SearchView extends BaseMvpView{
    void searchSuccess(ResponseData<ArticleList> data);
    void searchError(String msg);
    void loadMoreSuccess(ResponseData<ArticleList> data);
    void loadMoreError(String msg);
    void hotKeySuccess(ResponseData<List<HotKeyBean>> data);
    void hotKeyError(String msg);
}
