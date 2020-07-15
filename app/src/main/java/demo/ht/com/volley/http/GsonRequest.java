package demo.ht.com.volley.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import androidx.annotation.Nullable;

public class GsonRequest<T> extends Request<T> {
    public GsonRequest(String url, Response.ErrorListener listener) {
        super(url, listener);
    }

    public GsonRequest(int method, String url, @Nullable Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {

    }
}
