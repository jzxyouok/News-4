// (c)2016 Flipboard Inc, All Rights Reserved.

package com.hao.news.network;


import com.google.gson.annotations.SerializedName;
import com.hao.news.model.GankBeautyBean;

import java.util.List;


public class GankSerializedApi {
    //@SerializedName注解来将对象里的属性跟json里字段对应值匹配起来。
    public @SerializedName("results") List<GankBeautyBean> beauties;
}
