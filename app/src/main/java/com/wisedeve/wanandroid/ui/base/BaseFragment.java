package com.wisedeve.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisedeve.wanandroid.util.T;

import butterknife.ButterKnife;

/**
 * Description：
 * Created time：18-6-3 下午3:35
 * author：wisedeve
 * email：wisedeve@163.com
 */
public abstract class BaseFragment<P extends BasePresenter<V>, V extends BaseMvpView> extends Fragment {
    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(provideContentViewId(),container,false);
        ButterKnife.bind(this,rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void initView(View rootView) {

    }

    public void initData() {

    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract P createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    public void Toast(String msg){
        T.showShort(getContext(),msg);
    }
}
