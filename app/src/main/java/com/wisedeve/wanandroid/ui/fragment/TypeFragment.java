package com.wisedeve.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.adapter.ArticleListAdapter;
import com.wisedeve.wanandroid.ui.base.BaseFragment;
import com.wisedeve.wanandroid.ui.presenter.TypePresenter;
import com.wisedeve.wanandroid.ui.view.TypeView;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：
 * Created time：18-6-12 下午2:12
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class TypeFragment extends BaseFragment<TypePresenter, TypeView> implements TypeView, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tag_flowlayout)
    TagFlowLayout tagFlowlayout;
    @BindView(R.id.recycle_type)
    RecyclerView recycleType;
    private ArticleListAdapter mAdapter;


    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    protected TypePresenter createPresenter() {
        return new TypePresenter(getContext());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_type;
    }

    @Override
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public TagFlowLayout getFlowLayout() {
        return tagFlowlayout;
    }

    @Override
    public void initView(View rootView) {
        recycleType.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleListAdapter(getContext(),null,0);
        mAdapter.setOnLoadMoreListener(this,recycleType);
        recycleType.setAdapter(mAdapter);
        mPresenter.getTagData();
        recycleType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    tagFlowlayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData) {
        if (acticlrResponseData.getData().getDatas().size() != 0) {
            mAdapter.setNewData(acticlrResponseData.getData().getDatas());
        }
    }

    @Override
    public void getMoreDataSuccess(ResponseData<ArticleList> articleListResponseData) {
        if (articleListResponseData.getData().getDatas().size() != 0) {
            mAdapter.addData(articleListResponseData.getData().getDatas());
            mAdapter.loadMoreComplete();
        }else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getDataError(String errorMessage) {
        Toast(errorMessage);
    }


    @Override
    public void showProgress(String msg) {
    }
    @Override
    public void hideProgress() {
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }
}
