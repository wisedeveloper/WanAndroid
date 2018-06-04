package com.wisedeve.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContentProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.BannerBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.adapter.ArticleListAdapter;
import com.wisedeve.wanandroid.ui.base.BaseFragment;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.presenter.HomePresenter;
import com.wisedeve.wanandroid.ui.view.HomeView;
import com.wisedeve.wanandroid.util.ImageLoaderManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Description：
 * Created time：18-6-3 下午3:53
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class HomeFragment extends BaseFragment<HomePresenter,HomeView> implements HomeView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recycle_home)
    RecyclerView recycleHome;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private ArticleListAdapter mAdapter;
    private BGABanner bgaBanner;

    public static HomeFragment newInstance() {

        return new HomeFragment();

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View rootView) {
        recycleHome.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleListAdapter(getContext(),null);
        recycleHome.setAdapter(mAdapter);

        swipeRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this,recycleHome);

        View view = View.inflate(getActivity(),R.layout.layout_banner,null);
        bgaBanner = view.findViewById(R.id.banner);
        mAdapter.addHeaderView(view);
        onRefresh();
    }

    @Override
    public void showRefreshView(Boolean refresh) {
        swipeRefresh.setRefreshing(refresh);
    }

    @Override
    public void getBannerDataSuccess(ResponseData<List<BannerBean>> bannerBeanResponseData) {
        bgaBanner.setData(R.layout.item_banner,bannerBeanResponseData.getData(),null);
        bgaBanner.setAdapter(new BGABanner.Adapter<View,BannerBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, BannerBean model, int position) {
                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
                ImageLoaderManager.LoadImage(getContext(), model.getImagePath(), imageView);
            }
        });
    }

    @Override
    public void getRefreshDataSuccess(ResponseData<ArticleList> acticlrResponseData) {
        mAdapter.setNewData(acticlrResponseData.getData().getDatas());
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
    public void onRefresh() {
        mPresenter.getBannerData();
        mPresenter.getRefreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }
}
