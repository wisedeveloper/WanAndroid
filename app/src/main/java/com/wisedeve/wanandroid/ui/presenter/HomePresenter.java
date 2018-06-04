package com.wisedeve.wanandroid.ui.presenter;

import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.helper.RxObserver;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.BannerBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.view.HomeView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created time：18-6-3 下午4:34
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private int mCurrentPage;

    /**
     * 获取Banner数据
     */
    public void getBannerData(){
        ServiceApi.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<List<BannerBean>>>() {
                    @Override
                    public void _onNext(ResponseData<List<BannerBean>> bannerBeanResponseData) {
                        getMvpView().getBannerDataSuccess(bannerBeanResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                    }
                });
    }

    /**
     * 刷新
     */
    public void getRefreshData(){
        mCurrentPage = 0;
        ServiceApi.getHomeArticleData(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getMvpView().showRefreshView(true);
                    }

                    @Override
                    public void _onNext(ResponseData<ArticleList> acticlrResponseData) {
                        getMvpView().getRefreshDataSuccess(acticlrResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                        getMvpView().showRefreshView(false);
                    }

                    @Override
                    public void _onComplete() {
                        getMvpView().showRefreshView(false);
                    }
                });
    }

    /**
     * 加载更多
     */
    public void getMoreData(){
        mCurrentPage ++;
        ServiceApi.getHomeArticleData(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<ArticleList>>() {
                    @Override
                    public void _onNext(ResponseData<ArticleList> articleListResponseData) {
                        getMvpView().getMoreDataSuccess(articleListResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                    }
                });
    }

}
