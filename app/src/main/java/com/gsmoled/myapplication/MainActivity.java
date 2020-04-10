package com.gsmoled.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gsmoled.myapplication.NetWork.DownloadBean;
import com.gsmoled.myapplication.NetWork.GiteeRequestBean;
import com.gsmoled.myapplication.NetWork.NetWorkUtils;
import com.gsmoled.myapplication.NetWork.RequestUrlBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startRequest;
    private String downloadUrl;
    private boolean isCanDownload=false;
    private boolean isGiteeCanDownload=false;
    private Button startUpdata;
    private String baseUrl;
    private String url;
    private String TAG="MainActivity";
    private Button giteeStartRequest;
    private Button giteeStartUpdata;
    private String path;
    private String downloadUrl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        startRequest.setOnClickListener(this);
        startUpdata.setOnClickListener(this);
        giteeStartRequest.setOnClickListener(this);
        giteeStartUpdata.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        startRequest = findViewById(R.id.github_start_request);
        startUpdata = findViewById(R.id.github_start_updata);
        giteeStartRequest = findViewById(R.id.gitee_start_request);
        giteeStartUpdata = findViewById(R.id.gitee_start_updata);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.github_start_request:
                startRequest();
                break;
            case R.id.github_start_updata:
                if (isCanDownload) {
                    startUpdataVersion();
                }
                break;
            case R.id.gitee_start_request:
                giteeStartRequest();
                break;
            case R.id.gitee_start_updata:
                giteeStartDownload();
                break;
        }
    }

    private void giteeStartDownload() {
        //下载固件
        if (isGiteeCanDownload) {
            //下载固件
            new NetWorkUtils().getGiteeNetworkRequest(baseUrl).downloadFile(url).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
                @Override
                public void accept(ResponseBody responseBody) throws Exception {
                    try {
                        // todo change the file location/name according to your needs
                        //文件存储地址:SDCard//Android//data//com.gsmoled.myapplication//filesdfu_ota_app_v1.0.28.zip
                        File futureStudioIconFile = new File(getExternalFilesDir(null) + url);
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            byte[] fileReader = new byte[4096];
                            long fileSize = responseBody.contentLength();
                            long fileSizeDownloaded = 0;
                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(futureStudioIconFile);
                            while (true) {
                                int read = inputStream.read(fileReader);
                                if (read == -1) {
                                    break;
                                }
                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;
                                Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                            }
                            outputStream.flush();
                            Log.e("yanchuang","下载成功");
                        } catch (IOException e) {
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.e("yanchuang",throwable.toString());
                }
            });
        }else {
            Toast.makeText(this,"下载地址未请求成功!", LENGTH_SHORT).show();
        }
    }

    private void giteeStartRequest() {
        //请求下载固件地址
        new NetWorkUtils().getGiteeNetworkRequest("").getGiteeUrl("git/trees/master?access_token=f2f24e1a662c823f989223d3116f30f3&recursive=1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GiteeRequestBean>() {
            @Override
            public void accept(GiteeRequestBean responseBody) throws Exception {
                Log.e("yanchuang","数据获取成功");
                List<GiteeRequestBean.TreeBean> tree = responseBody.getTree();
                //最新固件的文件名
                path = tree.get(tree.size() - 1).getPath();
                getDownLoadUrl(path);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("yanchuang","数据获取失败");
            }
        });
    }

    private void getDownLoadUrl(String path) {
        new NetWorkUtils().getGiteeNetworkRequest("https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/contents/").
                getGiteeDownloadUrl(path+"?access_token=f2f24e1a662c823f989223d3116f30f3&ref=master").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DownloadBean>() {
            @Override
            public void accept(DownloadBean giteeRequestBean) throws Exception {
                //获取到下载的url地址
                downloadUrl1 = giteeRequestBean.getDownload_url();
                int i = downloadUrl1.indexOf("/dfu");
                baseUrl = downloadUrl1.substring(0, i+1);
                url = downloadUrl1.substring(i+1, downloadUrl1.length());
                Log.e("yanchuang","baseUrl="+baseUrl+"-----------------------"+"url="+url);
                if (!TextUtils.isEmpty(downloadUrl1)){
                    isGiteeCanDownload=true;
                }else {
                    isGiteeCanDownload=false;
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    private void startUpdataVersion() {
        //下载固件
            new NetWorkUtils().getNetworkRequest(baseUrl).downloadFile(url).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
                @Override
                public void accept(ResponseBody responseBody) throws Exception {
                    try {
                        // todo change the file location/name according to your needs\
                        //文件存储地址:SDCard//Android//data//com.gsmoled.myapplication//filesdfu_ota_app_v1.0.27.zip
                        File futureStudioIconFile = new File(getExternalFilesDir(null) + url);
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            byte[] fileReader = new byte[4096];
                            long fileSize = responseBody.contentLength();
                            long fileSizeDownloaded = 0;
                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(futureStudioIconFile);
                            while (true) {
                                int read = inputStream.read(fileReader);
                                if (read == -1) {
                                    break;
                                }
                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;
                                Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                            }
                            outputStream.flush();
                           Log.e("yanchuang","下载成功");
                        } catch (IOException e) {
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.e("yanchuang",throwable.toString());
                }
            });
        }

    private void startRequest() {
        //开始请求网络
        new NetWorkUtils().getNetworkRequest("").getLunBoDataToo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<RequestUrlBean>>() {
            @Override
            public void accept(ArrayList<RequestUrlBean> list) throws Exception {
                RequestUrlBean requestUrlBean = list.get(0);
                List<RequestUrlBean.AssetsBean> assets = requestUrlBean.getAssets();
                downloadUrl = assets.get(assets.size() - 1).getBrowser_download_url();
                int i = downloadUrl.indexOf("/dfu");
                baseUrl = downloadUrl.substring(0, i+1);
                url = downloadUrl.substring(i+1, downloadUrl.length());
                if (!TextUtils.isEmpty(downloadUrl)){
                    isCanDownload=true;
                }else {
                    isCanDownload=false;
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("yanchuang", "throwable=" + throwable);
                isCanDownload=false;
            }
        });
    }
}
