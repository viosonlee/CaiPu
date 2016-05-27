package lee.vioson.caipu.callback;

/**
 * Author:李烽
 * Date:2016-05-21
 * FIXME
 * Todo
 */

public abstract class RequestDataNotCache<T> implements RequestDataHandler<T> {
    @Override
    public void onCacheData(boolean isEmpty, Object data) {

    }

    @Override
    public void onRequestSuccess(T data) {
        onSuccess( data);
    }

    protected abstract void onSuccess(T data);

    @Override
    public void onRequestFailture(String msg) {
        onFailure( msg);
    }

    protected abstract void onFailure(String msg);

    @Override
    public void onRequestFinish() {
        onFinish();
    }

    protected abstract void onFinish();
}
