package com.wisedeve.wanandroid.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.ui.base.BaseActivity;
import com.wisedeve.wanandroid.ui.presenter.WebViewPresenter;
import com.wisedeve.wanandroid.ui.view.CommonWebView;
import com.wisedeve.wanandroid.util.SharesUtils;
import com.wisedeve.wanandroid.widget.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity<WebViewPresenter, CommonWebView> implements CommonWebView {

    public static final String WEB_URL = "web_url";

    @BindView(R.id.icon_return)
    IconFontTextView iconReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.icon_search)
    IconFontTextView iconSearch;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webView;

    private String mUrl;
    private CustomPopWindow mCustomPopWindow;

    public static void startAction(Context context, String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.loading);
        iconReturn.setVisibility(View.VISIBLE);
        iconSearch.setVisibility(View.VISIBLE);
        iconSearch.setText(R.string.ic_more);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUrl = getIntent().getStringExtra(WEB_URL);
        mPresenter.setWebView(webView,mUrl);
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @OnClick({R.id.icon_return, R.id.icon_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_return:
                finish();
                break;
            case R.id.icon_search:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popup_menu,null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(iconSearch,10,10);
                break;
        }
    }

    private void handleLogic(View contentView) {
        contentView.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharesUtils.share(WebViewActivity.this,webView.getUrl());
                mCustomPopWindow.dissmiss();
            }
        });
        contentView.findViewById(R.id.tv_copy_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmd = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmd.setPrimaryClip(ClipData.newPlainText(getString(R.string.copy_link), webView.getUrl()));
                Toast(getString(R.string.copy_link_success));
                mCustomPopWindow.dissmiss();
            }
        });
        contentView.findViewById(R.id.tv_open_by_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
                startActivity(intent);
                mCustomPopWindow.dissmiss();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
