package com.hao.news.network;

import com.hao.news.model.NewsBean;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Project_Name:AppNews
 * Author:朱永豪
 * Email:13838584575@163.com
 * on 2016/11/11.
 */

public interface Api {

    String key = "b477a8f05e4fad2b1d4179ba168e3e9e";

    String BASE_URL_NEWS = "http://v.juhe.cn/toutiao/";
    String BASE_URL_PIC = "http://gank.io/api/";
    String BASE_URL_PIC_URL = "http:/ww3.sinaimg.cn/";

    //http://v.juhe.cn/toutiao/index?type=top&key=b477a8f05e4fad2b1d4179ba168e3e9e
    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)

    @GET("index")
    Observable<NewsBean> getNews(@Query("type") String type, @Query("key") String key);

    @GET("data/福利/{number}/{page}")
    Observable<GankSerializedApi> getGankPic(@Path("number") int number, @Path("page") int page);

    //显示图片
    @GET
    Observable<ResponseBody> getBitmapFromNet(@Url String fileUrl);
}
