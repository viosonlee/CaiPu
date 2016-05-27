package lee.vioson.caipu.sqlite;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public interface SQLiteControlInf<T> {

    void addData(T object);

    void deleteData(T t);

    void resetData(int id, T t);

    void query();

}
