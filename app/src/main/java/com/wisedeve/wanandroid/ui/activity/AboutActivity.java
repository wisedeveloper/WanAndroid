package com.wisedeve.wanandroid.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.ui.base.BaseActivity;
import com.wisedeve.wanandroid.ui.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.aboutVersion)
    TextView aboutVersion;
    @BindView(R.id.aboutContent)
    TextView aboutContent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    public static void startAction(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    @SuppressLint("ResourceType")
    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_return));
        getSupportActionBar().setTitle(R.string.about);
        try {
            aboutVersion.setText(getPackageManager().getPackageInfo(getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        aboutContent.setText(Html.fromHtml(getString(R.string.about_content)));
        aboutContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
