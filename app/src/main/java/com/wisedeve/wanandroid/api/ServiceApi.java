package com.wisedeve.wanandroid.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okrx2.adapter.ObservableBody;
import com.wisedeve.wanandroid.model.ArticleList;
import com.wisedeve.wanandroid.model.BannerBean;
import com.wisedeve.wanandroid.model.HotKeyBean;
import com.wisedeve.wanandroid.model.ResponseData;
import com.wisedeve.wanandroid.model.UserBean;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Response;

/**
 * Description：玩Android 开放API   http://www.wanandroid.com/blog/show/2
 * Created time：18-6-1 上午10:15
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class ServiceApi {

    public static final String IS_LOGIN_KEY = "isLogin";
    public static final String USERNAME_KEY = "userName";

    public static final String BASE_URL = "http://wanandroid.com/";
    public static final String loginUrl = BASE_URL + "user/login";
    public static final String registerUrl = BASE_URL + "user/register";
    public static final String bannerUrl = BASE_URL + "banner/json";
    public static final String homeArticleUrl = BASE_URL + "article/list/%d/json";
    public static final String collectUrl = BASE_URL + "lg/collect/%d/json";
    public static final String unCollectUrl = BASE_URL + "lg/uncollect_originId/%d/json";
    //public static final String unCollectUrl = BASE_URL + "lg/uncollect/{0}/json";
    public static final String queryUrl = BASE_URL + "article/query/%d/json";
    public static final String hotKeyUrl = BASE_URL + "hotkey/json";

    private static Gson gson = new Gson();

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public static Observable<ResponseData<UserBean>> login(String username, String password){
        return OkGo.<ResponseData<UserBean>>post(loginUrl)
                .params("username",username)
                .params("password",password)
                .converter(new Converter<ResponseData<UserBean>>() {
                    @Override
                    public ResponseData<UserBean> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<UserBean>>() {}.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<ResponseData<UserBean>>());
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    public static Observable<ResponseData<UserBean>> register(String username, String password,String repassword){
        return OkGo.<ResponseData<UserBean>>post(registerUrl)
                .params("username",username)
                .params("password",password)
                .params("repassword",repassword)
                .converter(new Converter<ResponseData<UserBean>>() {
                    @Override
                    public ResponseData<UserBean> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<UserBean>>() {}.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 获取banner数据
     * @return
     */
    public static Observable<ResponseData<List<BannerBean>>> getBannerData(){
        return OkGo.<ResponseData<List<BannerBean>>>get(bannerUrl)
                .converter(new Converter<ResponseData<List<BannerBean>>>() {
                    @Override
                    public ResponseData<List<BannerBean>> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<List<BannerBean>>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 获取首页文章列表
     * @param page
     * @return
     */
    public static Observable<ResponseData<ArticleList>> getHomeArticleData(int page){
        return OkGo.<ResponseData<ArticleList>>get(String.format(homeArticleUrl,page))
                .converter(new Converter<ResponseData<ArticleList>>() {
                    @Override
                    public ResponseData<ArticleList> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<ArticleList>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 收藏站内文章
     * @param id
     * @return
     */
    public static Observable<ResponseData<String>> collectArticle(int id){
        return OkGo.<ResponseData<String>>post(String.format(collectUrl,id))
                .converter(new Converter<ResponseData<String>>() {
                    @Override
                    public ResponseData<String> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<String>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    public static Observable<ResponseData<String>> unCollectArticle(int id){
        return OkGo.<ResponseData<String>>post(String.format(unCollectUrl,id))
                .converter(new Converter<ResponseData<String>>() {
                    @Override
                    public ResponseData<String> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<String>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 搜索文章
     * @param page
     * @param key
     * @return
     */
    public static Observable<ResponseData<ArticleList>> queryArticle(int page,String key){
        return OkGo.<ResponseData<ArticleList>>post(String.format(queryUrl,page))
                .params("k",key)
                .converter(new Converter<ResponseData<ArticleList>>() {
                    @Override
                    public ResponseData<ArticleList> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<ArticleList>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }

    /**
     * 获取搜索热词你
     * @return
     */
    public static Observable<ResponseData<List<HotKeyBean>>> hotKey(){
        return OkGo.<ResponseData<List<HotKeyBean>>>get(hotKeyUrl)
                .converter(new Converter<ResponseData<List<HotKeyBean>>>() {
                    @Override
                    public ResponseData<List<HotKeyBean>> convertResponse(Response response) throws Throwable {
                        Type type = new TypeToken<ResponseData<List<HotKeyBean>>>() {
                        }.getType();
                        return gson.fromJson(response.body().string(),type);
                    }
                })
                .adapt(new ObservableBody<>());
    }
}
