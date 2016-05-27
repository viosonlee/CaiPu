package lee.vioson.caipu.callback;

/**
 * Author:李烽
 * Date:2016-05-25
 * FIXME
 * Todo
 */
public abstract class CacheDataHandler<T> implements RequestDataHandler<T> {

    @Override
    public void onCacheData(boolean isEmpty, T data) {
        onCacheDataBack(isEmpty,data);
    }

    public abstract void onCacheDataBack(boolean isEmpty, T data);

    @Override
    public void onRequestSuccess(T data) {

    }

    @Override
    public void onRequestFailture(String msg) {

    }

    @Override
    public void onRequestFinish() {

    }
}
