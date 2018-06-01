package com.wisedeve.wanandroid.model;

import java.io.Serializable;

/**
 * Description：
 * Created time：18-6-1 下午12:41
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class ResponseData<T> implements Serializable {
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
