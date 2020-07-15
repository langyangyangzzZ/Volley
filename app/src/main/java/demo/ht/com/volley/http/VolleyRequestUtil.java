package demo.ht.com.volley.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by szj 20200714
 *  implementation 'com.android.volley:volley:1.1.1'
 */

public class VolleyRequestUtil {

   private VolleyRequestUtil(){}

   private static volatile  VolleyRequestUtil mVolleyRequestUtil;

   private static Context mContext;

   private  RequestQueue requestQueue;
   public static VolleyRequestUtil getInstance(Context context){
       if (mVolleyRequestUtil == null) {
           synchronized (VolleyRequestUtil.class){
               if (mVolleyRequestUtil == null) {
                   mContext = context;

                   mVolleyRequestUtil = new VolleyRequestUtil();
               }
           }
       }
       return mVolleyRequestUtil;
   }


    /**
     *
     * @param url   请求GET接口
     * @param volleyListenerInterface
     */
    public void GETJsonObjectRequest(String url, VolleyListenerInterface volleyListenerInterface){

        getVolley();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                null,volleyListenerInterface.onResponse(),volleyListenerInterface.onErr());

        requestQueue.add(jsonObjectRequest);
    }


    /**
     *
     * @param url   用于请求文本接口
     * @param volleyStringInterface 用于实现请求接口
     */
    public void StringRequest(String url, VolleyStringInterface volleyStringInterface){
        getVolley();
        StringRequest stringRequest = new StringRequest(url, volleyStringInterface.onStringResponse(), volleyStringInterface.onErr());
        requestQueue.add(stringRequest);
    }


    /**
     *  获取RequestQueue对象
     */
   private void getVolley(){
       if (requestQueue == null) {
           requestQueue = Volley.newRequestQueue(mContext);
       }
   }

    /**
     *
     * @param url   请求POST接口
     * @param hashMap   请求POST参数
     * @param volleyListenerInterface
     */
    public void POSTJsonObjectRequest(String url, final HashMap hashMap, VolleyListenerInterface volleyListenerInterface){
        getVolley();
        JSONObject jsonObject = new JSONObject(hashMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,
                jsonObject,volleyListenerInterface.onResponse(),volleyListenerInterface.onErr()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                hashMap.put("Accept", "application/json");
                hashMap.put("Content-Type", "application/json; charset=UTF-8");
                return hashMap;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


    /**
     *
     * @param url   请求图片接口
     * @param maxWidth  解码此位图的最大宽度，或0表示无                                参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，
     *                                                                                  则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩
     * @param maxHeigth 解码此位图的最大高度，或为零表示无
     * @param rgb   设置颜色属性  Bitmap.Config.ARGB_8888 属性最好
     * @param volleyImageInterface 图片正确错误接口
     */
    public void ImageRequest(String url, int maxWidth, int maxHeigth, Bitmap.Config rgb, VolleyImageInterface volleyImageInterface){
        getVolley();
        ImageRequest imageRequest = new ImageRequest(
                url,
                volleyImageInterface.onBitmap(),
                maxWidth,
                maxHeigth,
                rgb,
                volleyImageInterface.onErr()
        );
        requestQueue.add(imageRequest);
    }


    /**
     *
     * @param view      设置的ImageView
     * @param uri       加载图片的路径
     * @param defaultImageResId   未加载出图片时的图片
     * @param errorImageResId       加载报错时的图片
     * @param maxWidth  图像的最大宽度
     * @param maxHeight 图像的最大高度
     * @param volleyImageLoaderLolderInterface  缓存接口
     */
    public  void ImageLoader(ImageView view ,String uri, int defaultImageResId, final int errorImageResId ,int maxWidth, int maxHeight, VolleyImageLoaderLolderInterface volleyImageLoaderLolderInterface){

        getVolley();

        ImageLoader imageLoader = new ImageLoader(requestQueue, volleyImageLoaderLolderInterface.onCache());

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);

        imageLoader.get(uri, imageListener,maxWidth,maxHeight);

    }

    /**
     *
     * @param view      设置的ImageView
     * @param uri       加载图片的路径
     * @param defaultImageResId   未加载出图片时的图片
     * @param errorImageResId       加载报错时的图片
     * @param volleyImageLoaderLolderInterface  缓存接口
     */
    public  void ImageLoader(ImageView view ,String uri, int defaultImageResId, final int errorImageResId  ,VolleyImageLoaderLolderInterface volleyImageLoaderLolderInterface){

        getVolley();


        ImageLoader imageLoader = new ImageLoader(requestQueue, volleyImageLoaderLolderInterface.onCache());

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);

        imageLoader.get(uri, imageListener);

    }

    /**
     *  Bitmap实现接口
     */
    public interface VolleyImageInterface {
        /**
         *
         * @return  返回Bitmap图片
         */
        public abstract  Response.Listener<Bitmap> onBitmap();

        /**
         *
         * @return  报错
         */
        public abstract Response.ErrorListener onErr();
    }

    /**
     *  ImageLolder实现可缓存图片接口
     */
    public interface VolleyImageLoaderLolderInterface {

        /**
         *
         * @return 缓存
         * ImageLoader.ImageCache 返回2个参数   getBitmap(String url)    参数一:返回当前请求的接口
         *                                      putBitmap(String url, Bitmap bitmap)    参数一:返回当前请求的接口,参数二:返回当前Bitmap对象
         *                                      主要用来进行缓存
         */
        public abstract ImageLoader.ImageCache onCache();
    }


    /**
     * JsonObject实现接口
     */
    public interface VolleyListenerInterface {
        /**
         *
         * @return  返回JSONObject接口对象
         */
        public abstract Response.Listener<JSONObject>  onResponse();

        /**
         *
         * @return 报错提示
         */
        public abstract Response.ErrorListener  onErr();
    }

    /**
     * 文本实现接口
     */
    public interface VolleyStringInterface{
        public Response.Listener<String> onStringResponse();

        public Response.ErrorListener onErr();
    }

}
