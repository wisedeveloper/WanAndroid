package com.wisedeve.wanandroid.ui.presenter;

import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.helper.RxObserver;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.HotKeyBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.view.SearchView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created time：18-6-11 下午2:10
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class SearchPresenter extends BasePresenter<SearchView>{

    private int mCurrentPage;

    public void getHotKey(){
        ServiceApi.hotKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<List<HotKeyBean>>>() {
                    @Override
                    public void _onNext(ResponseData<List<HotKeyBean>> listResponseData) {
                        getMvpView().hotKeySuccess(listResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().hotKeyError(errorMessage);
                    }
                });
    }

    public void search(String key){
        mCurrentPage = 0;
        ServiceApi.queryArticle(mCurrentPage,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onNext(ResponseData<ArticleList> data) {
                        getMvpView().searchSuccess(data);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().searchError(errorMessage);
                    }
                });
    }

    public void loadMore(String key){
        mCurrentPage ++;
        ServiceApi.queryArticle(mCurrentPage,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onNext(ResponseData<ArticleList> data) {
                        getMvpView().loadMoreSuccess(data);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().loadMoreError(errorMessage);
                    }
                });
    }
}
