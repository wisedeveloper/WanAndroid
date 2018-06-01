package com.wisedeve.wanandroid.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wisedeve.wanandroid.util.T;

import butterknife.ButterKnife;

/**
 * Description：
 * Created time：18-6-1 上午11:22
 * author：wisedeve
 * email：wisedeve@163.com
 */
public abstract class BaseMvpActivity<P extends BasePresenter<V>,V extends BaseMvpView> extends AppCompatActivity implements BaseMvpView{
    private ProgressDialog progressDialog;
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);//实例化progressDialog
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);//presenter与view连接
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
        excuteStatesBar();
    }

    //用于创建Presenter(由子类实现)
    protected abstract P createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();


    @Override
    public void showProgress(String msg) {
        progressDialog.setMessage(msg);//设置进度条加载内容
        if (! progressDialog.isShowing())//如果进度条没有显示
            progressDialog.show();//显示进度条
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//presenter与view断开连接
        super.onDestroy();
    }

    public void Toast(String msg){
        T.showShort(this,msg);
    }

    public void Toast(int msg){
        T.showShort(this,msg);
    }

    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
    private void excuteStatesBar(){
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows,
            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }

}
