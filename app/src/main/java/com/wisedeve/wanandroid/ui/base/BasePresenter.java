package com.wisedeve.wanandroid.ui.base;

/**
 * Description：
 * Created time：18-6-1 上午11:25
 * author：wisedeve
 * email：wisedeve@163.com
 */
public abstract class BasePresenter<V extends BaseMvpView> {
    public V mvpView;
    public void attachView(V view){
        this.mvpView = view;
    }
    public void detachView(){
        this.mvpView = null;
    }
    /**
     * 判断 view是否为空
     * @return
     */
    public  boolean isAttachView(){
        return mvpView != null;
    }
    /**
     * 返回目标view
     * @return
     */
    public  V getMvpView(){
        return mvpView;
    }
}
