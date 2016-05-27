package lee.vioson.caipu.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lee.vioson.caipu.callback.OnQueryHandler;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.sqlite.CaipulistDBHelper;

/**
 * Author:李烽
 * Date:2016-05-20
 * FIXME
 * Todo 收藏的菜谱控制类
 */
public class CaipulistContorller {

    private static CaipulistContorller instance;

    private static CaipulistDBHelper dbHelper = null;

    private static SQLiteDatabase db = null;

    private CaipulistContorller() {

    }

    private static CaipulistDBHelper getDbHelper(Context context) {
        if (dbHelper == null)
            dbHelper = new CaipulistDBHelper(context);
        return dbHelper;
    }

    private static SQLiteDatabase getDb(Context context) {
        if (db == null)
            db = getDbHelper(context).getWritableDatabase();
        return db;
    }

    public static synchronized CaipulistContorller getInstance() {
        if (instance == null)
            synchronized (SearchHistoryController.class) {
                if (instance == null)
                    instance = new CaipulistContorller();
            }
        return instance;
    }


    /**
     * 不重复存储
     *
     * @param context
     * @param entity
     */
    public void addDataNotRepeat(final Context context, final CaipuList.TngouEntity entity) {
        getAllDatas(context, new OnQueryHandler<ArrayList<CaipuList.TngouEntity>>() {
            @Override
            public void onDataBack(ArrayList<CaipuList.TngouEntity> tngouEntities) {
                for (int i = 0; i < tngouEntities.size(); i++) {
                    int id = tngouEntities.get(i).getId();
                    int id1 = entity.getId();
                    if (id == id1) {
                        return;
                    }
                }
                addData(context, entity);
            }
        });
    }

    /**
     * 存储
     *
     * @param context
     * @param entity
     */
    public void addData(Context context, final CaipuList.TngouEntity entity) {
        ContentValues cv = new ContentValues();
        cv.put(CaipulistDBHelper.ID, entity.getId());
        cv.put(CaipulistDBHelper.KEY_WORD, entity.getKeywords());
        cv.put(CaipulistDBHelper.COUNT, entity.getCount());
        cv.put(CaipulistDBHelper.DESCRIPTION, entity.getDescription());
        cv.put(CaipulistDBHelper.FCOUNT, entity.getFcount());
        cv.put(CaipulistDBHelper.FOOD, entity.getFood());
        cv.put(CaipulistDBHelper.IMAGES, entity.getImages());
        cv.put(CaipulistDBHelper.IMG, entity.getImg());
        cv.put(CaipulistDBHelper.LIKE, entity.getLike());
        cv.put(CaipulistDBHelper.MESSAGE, entity.getMessage());
        cv.put(CaipulistDBHelper.NAME, entity.getName());
        cv.put(CaipulistDBHelper.RCOUNT, entity.getRcount());
        getDb(context).insert(CaipulistDBHelper.TABLE_NAME, null, cv);
    }

    public void addDatas(Context context, CaipuList caipuList) {
        if (caipuList == null)
            return;
        List<CaipuList.TngouEntity> tngou = caipuList.getTngou();
        for (int i = 0; i < tngou.size(); i++) {
            addData(context, tngou.get(i));
        }
    }

    /**
     * 删除
     *
     * @param context
     * @param entity
     */
    public void remove(Context context, CaipuList.TngouEntity entity) {
        String whereClause = CaipulistDBHelper.ID + "=?";
        String[] whereArgs = {entity.getId() + ""};
        getDb(context).delete(CaipulistDBHelper.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * 获得所有数据
     *
     * @param context
     * @param handler
     */
    public void getAllDatas(Context context, OnQueryHandler<ArrayList<CaipuList.TngouEntity>> handler) {
        ArrayList<CaipuList.TngouEntity> data = new ArrayList<>();
        Cursor cursor = getDb(context).query(CaipulistDBHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CaipuList.TngouEntity entity = new CaipuList.TngouEntity();
            entity.setName(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.NAME)));
            entity.setCount(cursor.getInt(cursor.getColumnIndex(CaipulistDBHelper.COUNT)));
            entity.setDescription(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.DESCRIPTION)));
            entity.setFcount(cursor.getInt(cursor.getColumnIndex(CaipulistDBHelper.FCOUNT)));
            entity.setFood(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.FOOD)));
            entity.setId(cursor.getInt(cursor.getColumnIndex(CaipulistDBHelper.ID)));
            entity.setImages(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.IMAGES)));
            entity.setImg(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.IMG)));
            entity.setKeywords(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.KEY_WORD)));
            entity.setLike(cursor.getInt(cursor.getColumnIndex(CaipulistDBHelper.LIKE)));
            entity.setMessage(cursor.getString(cursor.getColumnIndex(CaipulistDBHelper.MESSAGE)));
            entity.setRcount(cursor.getInt(cursor.getColumnIndex(CaipulistDBHelper.RCOUNT)));
            data.add(entity);
        }
        cursor.close();
        handler.onDataBack(data);
    }


    public static void onDestroy() {
        instance = null;
    }


    public void matchLikeCaipu(Context context, final List<CaipuList.TngouEntity> surseData,
                               final OnQueryHandler<List<CaipuList.TngouEntity>> handler
    ) {
        getAllDatas(context, new OnQueryHandler<ArrayList<CaipuList.TngouEntity>>() {
            @Override
            public void onDataBack(ArrayList<CaipuList.TngouEntity> tngouEntities) {
                for (int i = 0; i < surseData.size(); i++) {
                    int id = surseData.get(i).getId();
                    for (int i1 = 0; i1 < tngouEntities.size(); i1++) {
                        int id1 = tngouEntities.get(i1).getId();
                        if (id == id1) {
                            surseData.set(i, tngouEntities.get(i1));
                        }
                    }
                }
                handler.onDataBack(surseData);
            }
        });

    }


}
