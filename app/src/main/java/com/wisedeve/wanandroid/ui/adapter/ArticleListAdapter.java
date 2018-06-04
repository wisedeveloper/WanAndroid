package com.wisedeve.wanandroid.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wisedeve.wanandroid.R;
import com.wisedeve.wanandroid.model.ArticleBean;
import com.wisedeve.wanandroid.widget.IconFontTextView;

import java.util.List;

/**
 * Description：
 * Created time：18-6-4 上午9:26
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean,BaseViewHolder> {

    private Context mContext;

    public ArticleListAdapter(Context context,@Nullable List<ArticleBean> data) {
        super(R.layout.item_article,data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, ArticleBean item) {
        holder.setText(R.id.tv_author,item.getAuthor())
                .setText(R.id.tv_time,item.getNiceDate())
                .setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_type,item.getChapterName());
        IconFontTextView tvCollect = holder.getView(R.id.icon_collect);
        if (item.isCollect()) {
            tvCollect.setText(mContext.getResources().getText(R.string.ic_collect_sel));
            tvCollect.setTextColor(mContext.getResources().getColor(R.color.app_blue));
        }else {
            tvCollect.setText(mContext.getResources().getText(R.string.ic_collect_nor));
            tvCollect.setTextColor(mContext.getResources().getColor(R.color.tab_nor_color));
        }
    }
}
