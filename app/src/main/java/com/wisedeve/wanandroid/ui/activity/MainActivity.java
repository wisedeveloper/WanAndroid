package com.wisedeve.wanandroid.ui.activity;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.ui.base.BaseActivity;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.widget.IconFontTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：
 * Created time：18-6-1 上午11:08
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.icon_search)
    IconFontTextView iconSearch;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.icon_home)
    IconFontTextView iconHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.icon_type)
    IconFontTextView iconType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.icon_user)
    IconFontTextView iconUser;
    @BindView(R.id.tv_user)
    TextView tvUser;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTabColor(iconHome,tvHome);
    }

    @OnClick({R.id.icon_search, R.id.ll_home, R.id.ll_type, R.id.ll_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_search:
                break;
            case R.id.ll_home:
                setTabColor(iconHome,tvHome);

                break;
            case R.id.ll_type:
                setTabColor(iconType,tvType);

                break;
            case R.id.ll_user:
                setTabColor(iconUser,tvUser);

                break;
        }
    }

    private void setTabColor(IconFontTextView icon,TextView text){
        iconHome.setTextColor(getResources().getColor(R.color.tab_nor_color));
        tvHome.setTextColor(getResources().getColor(R.color.tab_nor_color));
        iconType.setTextColor(getResources().getColor(R.color.tab_nor_color));
        tvType.setTextColor(getResources().getColor(R.color.tab_nor_color));
        iconUser.setTextColor(getResources().getColor(R.color.tab_nor_color));
        tvUser.setTextColor(getResources().getColor(R.color.tab_nor_color));
        icon.setTextColor(getResources().getColor(R.color.tab_sel_color));
        text.setTextColor(getResources().getColor(R.color.tab_sel_color));

    }
}
