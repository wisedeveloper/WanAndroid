package com.wisedeve.wanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.adapter.ArticleListAdapter;
import com.wisedeve.wanandroid.ui.base.BaseActivity;
import com.wisedeve.wanandroid.ui.presenter.CollectPresenter;
import com.wisedeve.wanandroid.ui.view.CollectView;
import com.wisedeve.wanandroid.widget.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends BaseActivity<CollectPresenter, CollectView> implements CollectView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycle_collect)
    RecyclerView recycleCollect;
    @BindView(R.id.icon_return)
    IconFontTextView iconReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.icon_search)
    IconFontTextView iconSearch;

    private ArticleListAdapter mAdapter;

    public static void startAction(Context context) {
        context.startActivity(new Intent(context, CollectActivity.class));
    }

    @Override
    public void initView() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.my_collect);
        iconReturn.setVisibility(View.VISIBLE);
        iconSearch.setVisibility(View.GONE);

        recycleCollect.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleListAdapter(this, null,1);
        mAdapter.setOnLoadMoreListener(this, recycleCollect);
        recycleCollect.setAdapter(mAdapter);

        mPresenter.getRefreshData();
    }

    @Override
    protected CollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    public void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData) {
        if (acticlrResponseData.getData().getDatas().size() != 0) {
            mAdapter.setNewData(acticlrResponseData.getData().getDatas());
        }else {
            Toast(getResources().getString(R.string.no_data));
        }
    }

    @Override
    public void getMoreDataSuccess(ResponseData<ArticleList> articleListResponseData) {
        if (articleListResponseData.getData().getDatas().size() != 0) {
            mAdapter.addData(articleListResponseData.getData().getDatas());
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getDataError(String errorMessage) {
        Toast(errorMessage);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }

    @OnClick(R.id.icon_return)
    public void onClick() {
        finish();
    }
}
