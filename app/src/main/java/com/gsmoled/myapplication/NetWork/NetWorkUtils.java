package com.gsmoled.myapplication.NetWork;

import android.text.TextUtils;
import android.webkit.WebSettings;

import com.gsmoled.myapplication.MainActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建作者       : albus
 * 创建时间       : 2020/4/3
 * Fuction(类描述):
 */
public class NetWorkUtils {
//    https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/trees/master?access_token=f2f24e1a662c823f989223d3116f30f3&recursive=1
    private String githubUrl="https://api.github.com/repos/smile16/smartBandFirmwareVersion/";
    private String giteeUrl="https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/";
    public NetWorkRequestMode getNetworkRequest(String uri){
        if (!TextUtils.isEmpty(uri)){
            this.githubUrl=uri;
        }
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(githubUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.client(okHttpClientBuilder.build()).build();
        NetWorkRequestMode networkRequestMode = retrofit.create(NetWorkRequestMode.class);
        return networkRequestMode;
    }
    public NetWorkRequestMode getGiteeNetworkRequest(String uri){
        if (!TextUtils.isEmpty(uri)){
            this.giteeUrl=uri;
        }
        if (!TextUtils.isEmpty(uri)){
            this.giteeUrl=uri;
        }
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(giteeUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.client(okHttpClientBuilder.build()).build();
        NetWorkRequestMode networkRequestMode = retrofit.create(NetWorkRequestMode.class);
        return networkRequestMode;
    }

}
