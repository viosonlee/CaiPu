package lee.vioson.caipu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author:李烽
 * Date:2016-05-20
 * FIXME
 * Todo
 */
public class CaipulistDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "like_caipu.db";

    public static final String TABLE_NAME = "my_save_like";

    public static final String COUNT = "count";
    public static final String DESCRIPTION = "description";
    public static final String FCOUNT = "fcount";
    public static final String FOOD = "food";
    public static final String ID = "id";
    public static final String IMAGES = "images";
    public static final String IMG = "img";
    public static final String LIKE = "like";
    public static final String MESSAGE = "message";
    public static final String KEY_WORD = "keywords";
    public static final String NAME = "name";
    public static final String RCOUNT = "rcount";


    public CaipulistDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + ID + " INTEGER," + COUNT + " INTEGER," + DESCRIPTION + " TEXT,"
                + FCOUNT + " INTEGER," + FOOD + " TEXT," + IMAGES + " TEXT," + IMG + " TEXT," + LIKE + " INTEGER,"
                + MESSAGE + " TEXT," + KEY_WORD + " TEXT," + NAME + " TEXT," + RCOUNT + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
