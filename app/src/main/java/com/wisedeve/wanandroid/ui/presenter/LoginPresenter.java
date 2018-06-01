package com.wisedeve.wanandroid.ui.presenter;

import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.helper.RxObserver;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.model.UserBean;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.view.LoginView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created time：18-6-1 下午12:31
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public void login(){
        ServiceApi.login(getMvpView().getUsername(),getMvpView().getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<UserBean>>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getMvpView().showProgress("正在登陆...");
                    }

                    @Override
                    public void _onNext(ResponseData<UserBean> userBeanResponseData) {
                        getMvpView().loginSuccess(userBeanResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().loginError(errorMessage);
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void _onComplete() {
                        getMvpView().hideProgress();
                    }
                });
    }

    public void register(){
        ServiceApi.register(getMvpView().getUsername(),getMvpView().getPassword(),getMvpView().getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<UserBean>>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getMvpView().showProgress("正在注册...");
                    }

                    @Override
                    public void _onNext(ResponseData<UserBean> userBeanResponseData) {
                        getMvpView().registerSuccess(userBeanResponseData);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().registerError(errorMessage);
                        getMvpView().hideProgress();
                    }

                    @Override
                    public void _onComplete() {
                        getMvpView().hideProgress();
                    }
                });
    }
}
