package com.gsmoled.myapplication.NetWork;


import com.gsmoled.myapplication.MainActivity;

import java.util.ArrayList;
import java.util.PriorityQueue;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * 创建作者       : albus
 * 创建时间       : 2020/4/3
 * Fuction(类描述):
 */
public interface NetWorkRequestMode {
    //github 获取固件接口
    @GET("releases")
    Observable<ArrayList<RequestUrlBean>> getLunBoDataToo();


    @GET()
    Observable<ResponseBody> downloadFile(@Url String url);


//    @Headers({"access_token: f2f24e1a662c823f989223d3116f30f3","recursive: 1"})
    @GET()
    Observable<GiteeRequestBean> getGiteeUrl(@Url String url);
    @GET()
    Observable<DownloadBean> getGiteeDownloadUrl(@Url String url);
}
