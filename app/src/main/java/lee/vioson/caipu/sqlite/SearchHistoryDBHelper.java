package lee.vioson.caipu.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
@Deprecated
public class SearchHistoryDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "caipu.db";//数据库名称
    private static final int version = 1;//版本号

    public static final String TABLE_NAME = "search_history_table";

    public static final String KEY_WORD = "keyword";
    public static final String ID = "_id";


    public SearchHistoryDBHelper(Context context) {
        this(context, DB_NAME, null, version);
    }

    public SearchHistoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public SearchHistoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                 int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + KEY_WORD + " varchar not null UNIQUE)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
