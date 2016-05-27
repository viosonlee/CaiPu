package lee.vioson.caipu.callback;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public interface RequestDataHandler<T> {
     String TAG = "RequestDataHandler";

    void onCacheData(boolean isEmpty, T data);

    void onRequestSuccess(T data);

    void onRequestFailture(String msg);

    void onRequestFinish();
}
