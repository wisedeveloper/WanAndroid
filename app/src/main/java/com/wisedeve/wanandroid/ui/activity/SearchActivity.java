package com.wisedeve.wanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.HotKeyBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.ui.adapter.ArticleListAdapter;
import com.wisedeve.wanandroid.ui.base.BaseActivity;
import com.wisedeve.wanandroid.ui.presenter.SearchPresenter;
import com.wisedeve.wanandroid.ui.view.SearchView;
import com.wisedeve.wanandroid.widget.IconFontTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresenter, SearchView> implements SearchView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.recycle_search)
    RecyclerView recycleSearch;
    @BindView(R.id.icon_return)
    IconFontTextView iconReturn;
    @BindView(R.id.icon_clean_input)
    IconFontTextView iconClean;
    @BindView(R.id.tag_flowlayout)
    TagFlowLayout mFlowLayout;

    private String keyWord;
    private ArticleListAdapter mAdapter;

    public static void startAction(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        iconReturn.setVisibility(View.VISIBLE);
        rlSearch.setVisibility(View.VISIBLE);

        mPresenter.getHotKey();

        recycleSearch.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleListAdapter(this,null,0);
        recycleSearch.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this,recycleSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyWord = s.toString().trim();
                if (keyWord.length()>0) {
                    iconClean.setVisibility(View.VISIBLE);

                }else {
                    iconClean.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(keyWord)) {
                        mPresenter.search(keyWord);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void searchSuccess(ResponseData<ArticleList> data) {
        if (data.getData().getDatas() == null || data.getData().getDatas().size() == 0) {
            Toast("未搜到关键字相关的文章");
        }else {
            recycleSearch.setVisibility(View.VISIBLE);
            mFlowLayout.setVisibility(View.GONE);
            mAdapter.setNewData(data.getData().getDatas());
        }
    }

    @Override
    public void searchError(String msg) {
        Toast(msg);
    }

    @Override
    public void loadMoreSuccess(ResponseData<ArticleList> data) {
        if (data.getData().getDatas() == null || data.getData().getDatas().size() == 0) {
            mAdapter.loadMoreEnd();
        }else {
            mAdapter.addData(data.getData().getDatas());
            mAdapter.loadMoreComplete();
        }

    }

    @Override
    public void loadMoreError(String msg) {
        Toast(msg);
    }

    @Override
    public void hotKeySuccess(ResponseData<List<HotKeyBean>> data) {
        mFlowLayout.setAdapter(new TagAdapter<HotKeyBean>(data.getData()) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyBean hotKeyBean) {
                TextView hotKey = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_hot_key,parent,false);
                hotKey.setText(hotKeyBean.getName());
                return hotKey;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                etSearch.setText(data.getData().get(position).getName());
                // 将光标移至字符串尾部
                CharSequence charSequence = etSearch.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                return true;
            }
        });
    }

    @Override
    public void hotKeyError(String msg) {
        Toast(msg);
    }

    @OnClick({R.id.icon_clean_input, R.id.icon_search,R.id.icon_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_clean_input:
                etSearch.setText("");
                iconClean.setVisibility(View.GONE);
                mFlowLayout.setVisibility(View.VISIBLE);
                recycleSearch.setVisibility(View.GONE);
                break;
            case R.id.icon_search:
                if (!TextUtils.isEmpty(keyWord)) {
                    mPresenter.search(keyWord);
                }
                break;
            case R.id.icon_return:
                finish();
                break;
        }
    }


    @Override
    public void onLoadMoreRequested() {
        if (!TextUtils.isEmpty(keyWord)) {
            mPresenter.loadMore(keyWord);
        }
    }
}
