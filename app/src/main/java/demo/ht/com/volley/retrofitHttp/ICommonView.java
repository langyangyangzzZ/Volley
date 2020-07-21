package demo.ht.com.volley.retrofitHttp;

public interface ICommonView<S> {
    void onSuccess(int whichApi, S successResult);
    void onFailed(int whichApi, Throwable failedResult);
}
