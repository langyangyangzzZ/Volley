package demo.ht.com.volley;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.volley.http.VolleyRequestUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage,image2;
    private String STRINGREQUEST_URL = "StringRequest只建议用来请求文本接口  可以用https://www.baidu.com接口来尝试";

    //是否封装volley
    private Boolean isEncapsulated = true;//true封装 flase不封装

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);

        if (isEncapsulated) {
            //封装Volley
            Encapsulated();
        }else{
            //未封装volley
            NotEncapsulated();
        }
    }

    /**
     *  封装volley
     */
    private void Encapsulated() {
        VolleyRequestUtil.getInstance(this).StringRequest(STRINGREQUEST_URL, new VolleyRequestUtil.VolleyStringInterface() {
            @Override
            public Response.Listener<String> onStringResponse() {
                return new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Log.i("VolleyStringRequest",response);
                    }
                };
            }

            @Override
            public Response.ErrorListener onErr() {
                return new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyStringRequest",error.getMessage());
                    }
                };
            }
        });


        VolleyRequestUtil.getInstance(this).GETJsonObjectRequest("http://www.weather.com.cn/data/city3jdata/china.html", new VolleyRequestUtil.VolleyListenerInterface() {
            @Override
            public Response.Listener<JSONObject> onResponse() {
                return new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("onResponse", response.toString());
                    }
                };
            }

            @Override
            public Response.ErrorListener onErr() {
                return new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse", error.getMessage());
                    }
                };
            }
        });

        HashMap<String, String> hashMap = new HashMap<>();
        VolleyRequestUtil.getInstance(this).ImageRequest("http://api.map.baidu.com/images/weather/day/zhenyu.png", 0, 0, Bitmap.Config.ARGB_8888, new VolleyRequestUtil.VolleyImageInterface() {
            @Override
            public Response.Listener<Bitmap> onBitmap() {
                return new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImage.setImageBitmap(response);
                    }
                };
            }
            @Override
            public Response.ErrorListener onErr() {
                return new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse", error.getMessage());
                    }
                };
            }
        });




        VolleyRequestUtil.getInstance(this).POSTJsonObjectRequest("", hashMap, new VolleyRequestUtil.VolleyListenerInterface() {
            @Override
            public Response.Listener<JSONObject> onResponse() {
                return null;
            }

            @Override
            public Response.ErrorListener onErr() {
                return null;
            }
        });
    }

    /**
     * 为封装Volley
     */
    private void NotEncapsulated() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(STRINGREQUEST_URL, new Response.Listener<String>() {
            @Override//正确返回
            public void onResponse(String response) {
                Log.i("StringRequestResponse",response);
            }   //错误返回
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("onErrorResponse",error.getMessage());
            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"POST接口", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("JsonObjectResponse", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JsonObjectResponse", error.getMessage()+"");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("map1","1");
                hashMap.put("map2","2");
                hashMap.put("map3","3");
                return hashMap;
            }
        };

        ImageRequest imageRequest = new ImageRequest("http://api.map.baidu.com/images/weather/day/zhenyu.png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.i("ImageRequestResponse","1111");
//                    mImage.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("onErrorResponse111",error.getMessage()+"");
            }
        });



        queue.add(imageRequest);
        queue.add(jsonObjectRequest);
        queue.add(stringRequest);
    }

    public void onClickButton(View view) {
        if (isEncapsulated) {
                        //封装volley
                        VolleyRequestUtil.getInstance(this).ImageLoader(image2,
                                "http://api.map.baidu.com/images/weather/night/duoyun.png",
                                R.drawable.ic_launcher_foreground,
                                R.drawable.ic_launcher_background,
                                new VolleyRequestUtil.VolleyImageLoaderLolderInterface() {
                                    @Override
                                    public ImageLoader.ImageCache onCache() {
                                        return new ImageLoader.ImageCache() {
                                            @Override
                                            public Bitmap getBitmap(String url) {
                                                return null;
                                            }

                                            @Override
                                            public void putBitmap(String url, Bitmap bitmap) {

                                            }
                                        };
                                    }
                                }
                        );
        }else{
            //未封装volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            /**
             * 参数一:RequestQueue对象
             * 参数二:ImageCache()对象
             */
            ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                @Override   //
                public Bitmap getBitmap(String url) {
                    Log.i("ImageLoadergetBitmap",url+"");
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    image2.setImageBitmap(bitmap);
                    Log.i("ImageLoaderputBitmap",url+"\t\t"+bitmap);
                }
            });

            /**
             *     参数一:需要设置的ImageView对象
             *     参数二:在图片加载过程中显示默认图片
             *     参数三:图片加载失败显示
             */
            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(mImage, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background);

            /**
             *  参数一:图片接口
             *  参数二:  ImageLoader.ImageListener对象
             *  参数三:最大宽度
             *  参数四:最大高度
             */
            imageLoader.get("http://api.map.baidu.com/images/weather/night/duoyun.png",imageListener,200,200);



        }




    }
}
