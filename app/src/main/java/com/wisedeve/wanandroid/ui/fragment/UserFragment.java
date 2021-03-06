package com.wisedeve.wanandroid.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.api.ServiceApi;
import com.wisedeve.wanandroid.ui.activity.AboutActivity;
import com.wisedeve.wanandroid.ui.activity.CollectActivity;
import com.wisedeve.wanandroid.ui.activity.LoginActivity;
import com.wisedeve.wanandroid.ui.base.BaseFragment;
import com.wisedeve.wanandroid.ui.base.BasePresenter;
import com.wisedeve.wanandroid.util.SPUtils;
import com.wisedeve.wanandroid.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description：
 * Created time：18-6-5 下午6:14
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.btn_collect)
    Button btnCollect;
    @BindView(R.id.btn_about)
    Button btnAbout;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_night_mode)
    Button btnNightMode;
    private int nightMode;

    public static UserFragment newInstance(){
        return new UserFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(View rootView) {
        nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            btnNightMode.setText(R.string.night_mode);
        }else if (nightMode == Configuration.UI_MODE_NIGHT_NO) {
            btnNightMode.setText(R.string.day_mode);
        }
        if (SPUtils.getBoolean(getContext(), ServiceApi.IS_LOGIN_KEY,false)) {
            btnLogin.setText(R.string.exit_login);
            tvUsername.setText(SPUtils.getString(getContext(),ServiceApi.USERNAME_KEY,""));
        }else {
            btnLogin.setText(R.string.click_login);
            tvUsername.setText(R.string.no_login);
        }
    }

    @OnClick({R.id.btn_collect, R.id.btn_about, R.id.btn_login, R.id.btn_night_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_collect:
                if (!SPUtils.getBoolean(getContext(), ServiceApi.IS_LOGIN_KEY,false)) {
                    T.showShort(getContext(),"请先登录");
                    LoginActivity.startAction(getContext());
                    return;
                }
                CollectActivity.startAction(getActivity());
                break;
            case R.id.btn_about:
                AboutActivity.startAction(getActivity());
                break;
            case R.id.btn_login:
                if (SPUtils.getBoolean(getContext(), ServiceApi.IS_LOGIN_KEY,false)) {
                    SPUtils.setBoolean(getContext(), ServiceApi.IS_LOGIN_KEY,false);
                    btnLogin.setText(R.string.click_login);
                    tvUsername.setText(R.string.no_login);
                }else {
                    LoginActivity.startAction(getActivity());
                }
                break;
            case R.id.btn_night_mode:
                if(nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    btnNightMode.setText(R.string.day_mode);
                } else if(nightMode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    btnNightMode.setText(R.string.night_mode);
                }
                getActivity().recreate();
                break;
        }
    }
}
