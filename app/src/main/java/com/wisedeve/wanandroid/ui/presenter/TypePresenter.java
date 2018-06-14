package com.wisedeve.wanandroid.ui.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.helper.RxObserver;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.model.TreeBean;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.ui.view.TypeView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created time：18-6-12 下午2:33
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class TypePresenter extends BasePresenter<TypeView> {

    private Context mContext;
    private List<TreeBean> treeBeanList;
    private TagFlowLayout mFlowLayout;
    private int mCurrentPage;
    private int articleTypeId;//文章列表所属分类id
    private boolean flag = true;
    private int mCurrentTab;//已选中的tab页面标记
    private int mCurrentTag;//已选中的tag分类标记

    public TypePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void getTagData(){
        ServiceApi.tree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<ResponseData<List<TreeBean>>>() {
                    @Override
                    public void _onNext(ResponseData<List<TreeBean>> listResponseData) {
                        treeBeanList = listResponseData.getData();
                        setTabUI();
                    }
                    @Override
                    public void _onError(String errorMessage) {
                        getMvpView().getDataError(errorMessage);
                    }
                });
    }

    /**
     * 设置体系数据,TabLayout
     */
    public void setTabUI(){
        TabLayout tabLayout = getMvpView().getTabLayout();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //先设置监听,默认选中第一个才会生效
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTagUI(tab.getPosition());
                mFlowLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (mFlowLayout.getVisibility() == View.GONE) {
                    mFlowLayout.setVisibility(View.VISIBLE);
                }else {
                    mFlowLayout.setVisibility(View.GONE);
                }

            }
        });

        for (int i = 0; i < treeBeanList.size(); i++) {
            if (i == 0) {
                //默认选中第一个体系
                tabLayout.addTab(tabLayout.newTab().setText(treeBeanList.get(i).getName()),true);
            }else {
                tabLayout.addTab(tabLayout.newTab().setText(treeBeanList.get(i).getName()));
            }
        }

    }

    /**
     * 设置二级体系数据,FlowLayout
     *
     * @param position
     */
    public void setTagUI(int position){
        mFlowLayout = getMvpView().getFlowLayout();
        TagAdapter mTagAdapter = new TagAdapter<TreeBean.ChildrenBean>(treeBeanList.get(position).getChildren()) {
            @Override
            public View getView(FlowLayout parent, int position, TreeBean.ChildrenBean hotKeyBean) {
                TextView hotKey = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_hot_key,parent,false);
                hotKey.setText(hotKeyBean.getName());
                return hotKey;
            }
        };
        mFlowLayout.setAdapter(mTagAdapter);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position_tag, FlowLayout parent) {
                //记录上次所选文章列表所属的体系及分类
                mCurrentTab = position;
                mCurrentTag = position_tag;
                articleTypeId = treeBeanList.get(mCurrentTab).getChildren().get(mCurrentTag).getId();
                refreshData();
                mFlowLayout.setVisibility(View.GONE);
                return true;
            }
        });
        //首次加载默认显示第一个tab体系的第一个tag分类
        if (flag) {
            mTagAdapter.setSelectedList(0);
            articleTypeId = treeBeanList.get(0).getChildren().get(0).getId();
            refreshData();
            flag = false;
        }
        //记录上次所选文章列表所属的体系及分类
        if (mCurrentTab == position) {
            mTagAdapter.setSelectedList(mCurrentTag);
        }

    }

    /**
     * 获取某体系文章列表
     *
     */
    public void refreshData(){
        mCurrentPage = 0;
        ServiceApi.treeArticle(mCurrentPage,articleTypeId)
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
        ServiceApi.treeArticle(mCurrentPage,articleTypeId)
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
