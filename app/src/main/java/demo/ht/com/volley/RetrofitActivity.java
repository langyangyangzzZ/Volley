package demo.ht.com.volley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.volley.bean.GuoChuangYunBean;
import demo.ht.com.volley.bean.ImageBean;
import demo.ht.com.volley.bean.MeiNvBean;
import demo.ht.com.volley.retrofitHttp.ApiConfig;
import demo.ht.com.volley.retrofitHttp.ApiServer;
import demo.ht.com.volley.retrofitHttp.ICommonView;
import demo.ht.com.volley.retrofitHttp.NetManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RetrofitActivity extends AppCompatActivity implements ICommonView {

    private TextView mTv;
    private ImageView mImage;
    private Boolean isEncapsulated = false;     //是否封装

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        mTv = findViewById(R.id.tv);
        mImage = findViewById(R.id.image);


    }

    /**
     *
     * @param view     GET点击事件
     */
    public void onClickGETButton(View view) {
        if (isEncapsulated ) {
            /**
             *      封装 使用GET请求接口
             */
            NetManager netManager = NetManager.getNetManager();
            ApiServer netService = netManager.getNetService(ApiServer.BaseUri);

            /**
             *      @GET   配合 @Path 注解 请求数据
             */
//            netManager.method(ApiConfig.GET_DATA_MEINV, netService.getMeiNvBean("8", "1"), this);

            /**
             *  @HTTP(method = "GET",path = "{number}/{index}" ,hasBody = false) 请求
             */
            netManager.method(ApiConfig.GET_DATA_MEINV, netService.getHttpMeiNvBean("8", "1"), this);

        }else {
            /**
             *      未封装 最原始请求接口
             */
            initGETUpData();
        }

    }

    /**
     *      未封装 GET最原始请求接口
     */
    private void initGETUpData() {
        Retrofit apiServer = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiServer.BaseUri)
                .build();

//        apiServer.create(ApiServer.class).getHttpMeiNvBean("8","1")
        apiServer.create(ApiServer.class).getMeiNvBean("8", "1")
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiNvBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiNvBean meiNvBean) {
                        mTv.setText(meiNvBean.toString());
                        Log.i("szjonNex", meiNvBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTv.setText("请求错误:" + e.getMessage());
                        Log.i("onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     *
     * @param view   POST点击事件
     */
    public void onClickPOSTButton(View view) {

        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("app_key", "74D2E724FE2B69EF7EA3F38E9400CF71");

        FormBody app_key = new FormBody.Builder()
                .add("app_key", "74D2E724FE2B69EF7EA3F38E9400CF71")
                .build();

        if (isEncapsulated) {
            NetManager netManager = NetManager.getNetManager();
            ApiServer netService = netManager.getNetService(ApiServer.GuoChuangYun_BaseUri);

            /**
             *   @POST 配合 @Body 注解请求数据
             */
//            netManager.method(ApiConfig.POST_UP_DATA_GUOCHUANGYUN,netService.getPOSTBodyGuoCHuangYunBean(app_key),this);

            /**
             * @FormUrlEncoded 配合 @FieldMap请求数据
             * 注意:要想使用@FiledMap 或者 @Filed就必须使用@FormUrlEncoded注解声明
             *      使用@FormUrlEncoded 就必须使用@FiledMap 或者 @Filed
             */
            netManager.method(ApiConfig.POST_UP_DATA_GUOCHUANGYUN,netService.getPOSTGuoCHuangYunBean(stringHashMap),this);


        }else{
            initPOSTUpData(stringHashMap,app_key);
        }



    }

    private void initPOSTUpData(HashMap<String, String> stringHashMap , FormBody app_key) {
        Retrofit apiServer = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiServer.GuoChuangYun_BaseUri)
                .build();


        apiServer.create(ApiServer.class)
//                .getPOSTBodyGuoCHuangYunBean(app_key)
                .getPOSTGuoCHuangYunBean(stringHashMap)
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuoChuangYunBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GuoChuangYunBean guoChuangYunBean) {
                        Log.i("szjPOSTonNext","\n"+guoChuangYunBean.toString());
                        mTv.setText("POST接口:\n" + guoChuangYunBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTv.setText("POST接口请求错误:\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public int REQUEST_PICTURE_CHOOSE = 1;

    public void onClickImageButton(View view) {

        /**
         *  打开本地相册 注意申请动态权限:
         */
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/png");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_PICTURE_CHOOSE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE_CHOOSE) {
            /**
             *  先获取系统返回的图片路径
             *  然后将图片上传,因为这里接口有问题,所以上传不成功!
             */
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);  //获取照片路径

            Log.i("szjpath", "\t\t" + path);

            initUpdata(path);
        }
    }

    /**
     *
     * @param path  图片路径
     */
    private void initUpdata(String path) {
        File file = new File(path);

        RequestBody requestBody = new MultipartBody.Builder().
                setType(MultipartBody.FORM)
                .addFormDataPart("app_key", "74D2E724FE2B69EF7EA3F38E9400CF71")
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();

        Log.i("initUpdataFile", file.getPath() + "\n" + file.getName());
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        RequestBody fb =RequestBody.create(MediaType.parse("text/plain"), "74D2E724FE2B69EF7EA3F38E9400CF71");

        if (isEncapsulated) {

            /**
             *      封装
             */
            NetManager netManager = NetManager.getNetManager();
            ApiServer netService = netManager.getNetService(ApiServer.GuoChuangYun_BaseUri);

            /**
             *    使用 @Body 注解 实现图片+参数一起上传
             */
//            netManager.method(ApiConfig.UP_DATA_IMAEG, netService.getImageBean(requestBody), this);


            /**
             *    使用 @Multipart注解 配合@Part 实现单独上传参数   和  单独上传图片
             */
            netManager.method(ApiConfig.UP_DATA_IMAEG, netService.uploadFile(fb,part), this);

        } else {
            /**
             *      最原始上传图片 + 参数
             */
            initRetrofitUpImageData(requestBody,fb,part);

        }

    }
    private void initRetrofitUpImageData(RequestBody body, RequestBody requestBody, MultipartBody.Part part) {
        Retrofit apiServer = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiServer.GuoChuangYun_BaseUri)
                .build();
        apiServer.create(ApiServer.class)
//                .getImageBean(requestBody)    //@Body 图片+参数 同时上传
                .uploadFile(requestBody,part)   //@Multipart 配合 @Part 单张图片 和单个参数上传
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        String url = imageBean.getData().getUrl();
                        Glide.with(RetrofitActivity.this)
                                .load(url)
                                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                                .into(mImage);
                        mTv.setText("图片上传成功:\n" + imageBean.toString());
                        Log.i("szjOnNext", imageBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError", e.getMessage());
                        mTv.setText("图片上传失败:\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onSuccess(int whichApi, Object successResult) {
        if (whichApi == ApiConfig.GET_DATA_MEINV) {

            MeiNvBean meiNvBean = (MeiNvBean) successResult;
            mTv.setText("使用成功:\n" + meiNvBean.toString());

        } else if (whichApi == ApiConfig.UP_DATA_IMAEG) {

            ImageBean imageBean = (ImageBean) successResult;
            Glide.with(this)
             .load(imageBean.getData().getUrl())
             .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
             .into(mImage);
            mTv.setText("使用成功:\n" + imageBean.toString());

        }else if(whichApi == ApiConfig.POST_UP_DATA_GUOCHUANGYUN){

            GuoChuangYunBean guoChuangYunBean = (GuoChuangYunBean) successResult;
            mTv.setText("使用成功:\n" + guoChuangYunBean.toString());
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable failedResult) {
        mTv.setText("使用失败:\n" + whichApi + "\n" + failedResult.getMessage());
    }


    public void onClickUrlButton(View view) {
        if (isEncapsulated) {
            /**
             *  封装
             */
            NetManager netManager = NetManager.getNetManager();
            ApiServer netService = netManager.getNetService(ApiServer.GuoChuangYun_BaseUri);
            netManager.method(ApiConfig.GET_DATA_MEINV,netService.getUrlMeiNvBean(ApiServer.BaseUri+"8/1"),this);

        }else{
            /**
             *  未封装
             */
            initGETUrlData();


        }


    }
    /**
     *  未封装
     */
    private void initGETUrlData() {
        Retrofit apiServer = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiServer.BaseUri)
                .build();

        apiServer.create(ApiServer.class).getUrlMeiNvBean(ApiServer.BaseUri+"8/1")
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiNvBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiNvBean meiNvBean) {
                        mTv.setText(meiNvBean.toString());
                        Log.i("szjonNex", meiNvBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTv.setText("请求错误:" + e.getMessage());
                        Log.i("onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
