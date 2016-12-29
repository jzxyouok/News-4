package com.hao.news.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Project_Name:AppNews
 * Author:朱永豪
 * Email:13838584575@163.com
 * on 2016/11/11.
 */

public class HttpUtils {

    private static Api api;

    /**
     * 获取新闻
     */
    public static Api getNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_NEWS)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        return api;
    }

    /**
     * 获取图片
     */
    public static Api getGankPic() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_PIC)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        return api;
    }
    /**
     * 根据图片url访问网络得到Bitmap
     */
    public static Api getBitmap() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_PIC_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        return api;
    }
}
