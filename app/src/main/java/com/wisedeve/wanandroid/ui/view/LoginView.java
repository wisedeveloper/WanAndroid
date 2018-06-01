package com.wisedeve.wanandroid.ui.view;

import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.model.UserBean;
import com.wisedeve.wanandroid.ui.base.BaseMvpView;

/**
 * Description：
 * Created time：18-6-1 上午11:16
 * author：wisedeve
 * email：wisedeve@163.com
 */
public interface LoginView extends BaseMvpView {
    String getUsername();
    String getPassword();
    void loginSuccess(ResponseData<UserBean> userBeanResponseData);
    void loginError(String errorMessage);
    void registerSuccess(ResponseData<UserBean> userBeanResponseData);
    void registerError(String errorMessage);
}
