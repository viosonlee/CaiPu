package lee.vioson.caipu.callback;

/**
 * Author:李烽
 * Date:2016-05-20
 * FIXME
 * Todo
 */
public interface OnQueryHandler<T> {
    String RESULT_EMPTY = "result object is empty";

    void onDataBack(T t);
}
