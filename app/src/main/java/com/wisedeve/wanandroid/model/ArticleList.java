package com.wisedeve.wanandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description：
 * Created time：18-6-3 下午4:21
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class ArticleList implements Serializable{

    /**
     * curPage : 68
     * datas : []
     * offset : 1340
     * over : true
     * pageCount : 67
     * size : 20
     * total : 1333
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleBean> datas) {
        this.datas = datas;
    }
}
