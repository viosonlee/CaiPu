package lee.vioson.caipu.control;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.utils.JSONUtils;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo 数据缓存
 */
public class CacheTool {
    private static final String SP_NAME = "DataCache";
    private static CacheTool instance;
    private static final String CAIPU_TYPES = "caipu_types";
    private static final String USER_CAIPU_TYPES = "user_caipu_types";
    private static final String FIRST_OPEN = "first";
    private static SharedPreferences sharedPreferences;


    private CacheTool() {
    }

    public static synchronized CacheTool getInstance() {
        if (instance == null)
            synchronized (CacheTool.class) {
                if (instance == null) {
                    instance = new CacheTool();
                }
            }
        return instance;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    public void saveCaipuTypes(Context context, String typeJson) {
        getSharedPreferences(context).edit().putString(CAIPU_TYPES, typeJson).apply();
    }

    public static String getCaipuTypes(Context context) {
        return getSharedPreferences(context).getString(CAIPU_TYPES, "{}");
    }

    public void saveCaipuTypes(Context context, CaiPuType caiPuType) {
        Gson gson = new Gson();
        String json = gson.toJson(caiPuType, CaiPuType.class);
        saveCaipuTypes(context, json);
    }


    public void saveUserFocusTypes(Context context, String json) {
        getSharedPreferences(context).edit().putString(USER_CAIPU_TYPES, json).apply();
    }

    public void saveUserFocusTypes(Context context, ArrayList<CaiPuType.TngouEntity> types) {
        String json = JSONUtils.toJson(types);
        saveUserFocusTypes(context, json);
    }

    /**
     * 获得用户选择的类型
     *
     * @param context
     * @return
     */
    public static ArrayList<CaiPuType.TngouEntity> getUserFocusTypes(Context context) {
        String string = getSharedPreferences(context).getString(USER_CAIPU_TYPES, "[]");
        return JSONUtils.fromJson(string, new TypeToken<ArrayList<CaiPuType.TngouEntity>>() {
        });
    }

    /**
     * 记录是否是第一次打开
     *
     * @param context
     * @param isFirstOpen
     */
    public void saveFirstOpen(Context context, boolean isFirstOpen) {
        getSharedPreferences(context).edit().putBoolean(FIRST_OPEN, isFirstOpen).apply();
    }

    public static boolean isFirstOpen(Context context) {
        return getSharedPreferences(context).getBoolean(FIRST_OPEN, true);
    }

    public static void onDestory() {
        instance = null;
    }
}
