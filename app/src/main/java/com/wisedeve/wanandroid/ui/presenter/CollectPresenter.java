package com.wisedeve.wanandroid.ui.presenter;

import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.helper.RxObserver;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.view.CollectView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created time：18-6-14 下午2:00
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class CollectPresenter extends BasePresenter<CollectView> {
    private int mCurrentPage;

    public void getRefreshData(){
        mCurrentPage = 0;
        ServiceApi.collectList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onNext(ResponseData<ArticleList> data) {
                        getMvpView().getRefreshDataSuccess(data);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                    }
                });
    }

    public void getMoreData(){
        mCurrentPage ++;
        ServiceApi.collectList(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onNext(ResponseData<ArticleList> data) {
                        getMvpView().getMoreDataSuccess(data);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                    }
                });
    }
}
