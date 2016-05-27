package lee.vioson.caipu.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import lee.vioson.caipu.sqlite.SearchHistoryDBHelper;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
@Deprecated
public class SearchHistoryController {

    private static SearchHistoryController instance;

    private static SearchHistoryDBHelper dbHelper = null;

    private static SQLiteDatabase db = null;

    private SearchHistoryController() {
    }

    private static SearchHistoryDBHelper getDbHelper(Context context) {
        if (dbHelper == null)
            dbHelper = new SearchHistoryDBHelper(context);
        return dbHelper;
    }

    private static SQLiteDatabase getDb(Context context) {
        if (db == null)
            db = getDbHelper(context).getWritableDatabase();
        return db;
    }

    public static synchronized SearchHistoryController getInstance() {
        if (instance == null)
            synchronized (SearchHistoryController.class) {
                if (instance == null)
                    instance = new SearchHistoryController();
            }
        return instance;
    }

    public static void onDestroy() {
        instance = null;

    }


    public void addData(Context context, String history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchHistoryDBHelper.KEY_WORD, history);
        getDb(context).replace(SearchHistoryDBHelper.TABLE_NAME, null, contentValues);
    }


    public void deleteData(Context context, String history) {
        String whereClause = SearchHistoryDBHelper.KEY_WORD + "=?";
        String[] whereArgs = {history};
        getDb(context).delete(SearchHistoryDBHelper.TABLE_NAME, whereClause, whereArgs);
    }


    public void resetData(Context context, int id, String history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchHistoryDBHelper.KEY_WORD, history);
        String whereClause = SearchHistoryDBHelper.ID + "=?";
        String[] whereArgs = {id + ""};
        getDb(context).update(SearchHistoryDBHelper.TABLE_NAME, contentValues, whereClause, whereArgs);
    }


    public Cursor getCursor(Context context) {
        return getDb(context).query(SearchHistoryDBHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    public void query() {

    }


}
