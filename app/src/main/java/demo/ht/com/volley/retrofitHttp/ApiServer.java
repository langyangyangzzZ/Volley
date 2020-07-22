package demo.ht.com.volley.retrofitHttp;


import java.util.Map;

import demo.ht.com.volley.bean.GuoChuangYunBean;
import demo.ht.com.volley.bean.ImageBean;
import demo.ht.com.volley.bean.MeiNvBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * 用于定义Retrofit接口
 */
public interface ApiServer {

    public static String BaseUri = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
//    http://hn216.api.yesapi.cn/
    public static String GuoChuangYun_BaseUri = "http://hn216.api.yesapi.cn/";

    /**
     *      通过调用getMeiNvBen()使用@Path注解,将传过来的参数设置给@GET()
     *      实现GET的传递
     */
    @GET("{number}/{index}")
    Observable<MeiNvBean> getMeiNvBean(@Path("number")String number, @Path("index")String index);

    /**
     *
     * @param method    网络请求的方法（区分大小写）
     * @param path      网络请求地址路径
     * @param hasBody  是否有请求体
     * @return
     */
    @HTTP(method = "GET",path = "{number}/{index}" ,hasBody = false)
    Observable<MeiNvBean> getHttpMeiNvBean(@Path("number")String number, @Path("index")String index);


    /**
     *
     * @param FormUrlEncoded   通过FieldMap上传必须加@FormUrlEncoded注解 表示声明表单上传
     * @return
     */
    @FormUrlEncoded
    @POST("?s=App.User.Search")
    Observable<GuoChuangYunBean> getPOSTGuoCHuangYunBean(@FieldMap Map<String ,String > map);

    /**
     *
     * @param body  POST请求体上传       FormBody app_key = new FormBody.Builder()
     *                                  .add("key", "valye")
     *                                  .build();
     * @return
     */
    @POST("?s=App.User.Search")
    Observable<GuoChuangYunBean> getPOSTBodyGuoCHuangYunBean(@Body RequestBody body);


    /**
     *
     * @param body  上传图片
     * @return
     */
//    @Multipart    注意 : @Multipart 是配合@Part或@PartMap使用的,单独不能使用!!!!!!!
    @POST("?s=App.CDN.UploadImg")
    Observable<ImageBean> getImageBean( @Body RequestBody file);

    /**\
     *
     * @param body  上传的app_key
     * @param file  上传的单张图片
     * @return
     */
    @Multipart
    @POST("?s=App.CDN.UploadImg")
    Observable<ImageBean> uploadFile(@Part("app_key") RequestBody body, @Part MultipartBody.Part file);


    @GET
    Observable<MeiNvBean> getUrlMeiNvBean(@Url String url);
}
