package demo.ht.com.volley.retrofitHttp;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.BuildConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


//        添加以下依赖::
////        Retrofit:
//          implementation 'com.squareup.retrofit2:retrofit:2.9.0'
//            implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
//
//
//            //glide4:		//用于图片处理
//            implementation 'com.github.bumptech.glide:glide:4.8.0'
//            annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
//
//            //RxJava:
//            implementation "io.reactivex.rxjava2:rxjava:2.1.3" // 必要rxjava2依赖
//            implementation "io.reactivex.rxjava2:rxandroid:2.0.1" // 必要rxandrroid依赖，切线程时需要用到
//            implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0' // 必要依赖，和Retrofit结合必须用到，下面会提到
//
//            /**
//             *      日志拦截器
//             */
//            implementation 'com.squareup.okhttp3:logging-interceptor:3.3.1'


/**
 *  20200721  szj
 */
public class NetManager {
    private static volatile NetManager sNetManager;

    private NetManager() {
    }

    public static NetManager getNetManager() {
        if (sNetManager == null) {//考虑效率问题
            synchronized (NetManager.class) {
                if (sNetManager == null) {//考虑多个线程问题
                    sNetManager = new NetManager();
                }
            }
        }
        return sNetManager;
    }

    /**
     *
     * @param t 传入接口前半段 注意以/结尾 返回 ApiServer
     * @param <T>
     * @return
     */
    public <T> ApiServer getNetService(T... t) {
        /**
         *      使用官方日志拦截器
         *      添加依赖:
         *      implementation 'com.squareup.okhttp3:logging-interceptor:3.3.1'
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        //日志拦截器
        OkHttpClient build = builder.build();

        ApiServer  service = new Retrofit.Builder()
                //传入可变参数是因为这里也可以使用默认接口ApiServer.BaseUri
                .baseUrl(t != null && t.length != 0 && !TextUtils.isEmpty((String) t[0]) ? (String) t[0] : ApiServer.BaseUri)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build().create(ApiServer.class);
        return service;
    }

    /**
     *
     * @param whichApi  ID编号 通过ID编号来区分当前请求的数据
     * @param pObservable      请求的接口
     * @param presenterCallBack 返回值
     * @param <T>
     */
    public <T> void method( final int whichApi,Observable<T> pObservable, final ICommonView presenterCallBack) {
        pObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onSuccess(Object value) {
                        presenterCallBack.onSuccess(whichApi, value);
                    }

                    @Override
                    public void onFailed(Throwable value) {
                        presenterCallBack.onFailed(whichApi, value);
                    }
                });
    }

    /**
     *
     * 用于POST请求 传入键值对 获取RequestBody
     * @param hashMap  hashMap键值对
     * @return
     */
    public  RequestBody getRequestBody(HashMap<String, String> hashMap) {
        StringBuffer data = new StringBuffer();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso = data.substring(0, data.length() - 1);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),jso);

        return requestBody;
    }
}
