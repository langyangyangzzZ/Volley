package demo.ht.com.volley.retrofitHttp;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver implements Observer {
    private Disposable d;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(Object value) {
        onSuccess(value);
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
        dispose();
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(Object value);

    public abstract void onFailed(Throwable value);

    private void dispose() {
        if (!d.isDisposed())
            d.dispose();
    }
}
