package lee.vioson.caipu.control;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.util.ArrayList;

import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.callback.CacheDataHandler;
import lee.vioson.caipu.callback.RequestDataHandler;
import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.utils.JSONUtils;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public class CaipuTypeHelper {
    private static final String TAG = "CaipuTypeHelper";
    private static CaipuTypeHelper instance;

    private ApiClient client;

    private CaipuTypeHelper() {
        client = new ApiClient();
    }

    public static synchronized CaipuTypeHelper getInstance() {
        if (instance == null)
            synchronized (CaipuTypeHelper.class) {
                if (instance == null)
                    instance = new CaipuTypeHelper();
            }
        return instance;
    }

    /**
     * 获取缓存数据
     *
     * @param context
     * @param handler
     */
    public void getCaipuTypeInCache(Context context, CacheDataHandler<CaiPuType> handler) {
        String caipuTypes = CacheTool.getCaipuTypes(context);
        Log.d(TAG, caipuTypes);
        Gson gson = new Gson();
        final CaiPuType caiPuType = gson.fromJson(caipuTypes, CaiPuType.class);
//        final CaiPuType caiPuType = JSONUtils.fromJson(caipuTypes, CaiPuType.class);
        handler.onCacheDataBack(caiPuType == null, caiPuType);
    }


    /**
     * 获取列表
     *
     * @param context
     * @param handler
     */
    public void getCaipuTypes(final Context context, final RequestDataHandler<CaiPuType> handler) {
        String caipuTypes = CacheTool.getCaipuTypes(context);
        Log.d(TAG, caipuTypes);
        Gson gson = new Gson();

        final CaiPuType caiPuType = gson.fromJson(caipuTypes, CaiPuType.class);
//        final CaiPuType caiPuType = JSONUtils.fromJson(caipuTypes, CaiPuType.class);
        handler.onCacheData(caiPuType == null, caiPuType);
        client.getTypeList(new HttpTool.ResultCallBack<CaiPuType>() {
            @Override
            protected void onError(Request request, Exception e) {
                handler.onRequestFailture(request.toString());
                handler.onRequestFinish();
            }

            @Override
            protected void onResponse(CaiPuType caiPuType1) {
                if (caiPuType1 != null)
                    CacheTool.getInstance().saveCaipuTypes(context, caiPuType1);
                handler.onRequestSuccess(caiPuType1);
                handler.onRequestFinish();
            }
        });
    }

    /**
     * 添加用户关注的
     *
     * @param context
     * @param type
     */
    public void addUserFocusType(Context context, CaiPuType.TngouEntity type) {
        ArrayList<CaiPuType.TngouEntity> userFocusTypes = CacheTool.getUserFocusTypes(context);
        for (int i = 0; i < userFocusTypes.size(); i++) {
            CaiPuType.TngouEntity tngouEntity = userFocusTypes.get(i);
            if (tngouEntity.getId() == type.getId())
                return;
        }
        userFocusTypes.add(type);
        CacheTool.getInstance().saveUserFocusTypes(context, userFocusTypes);
    }

    /**
     * 移除用户关心的
     *
     * @param context
     * @param type
     * @return
     */
    public boolean removeUserFocusType(Context context, CaiPuType.TngouEntity type) {
        ArrayList<CaiPuType.TngouEntity> userFocusTypes = CacheTool.getUserFocusTypes(context);
        for (int i = 0; i < userFocusTypes.size(); i++) {
            CaiPuType.TngouEntity tngouEntity = userFocusTypes.get(i);
            if (tngouEntity.getId() == type.getId()) {
                userFocusTypes.remove(type);
                CacheTool.getInstance().saveUserFocusTypes(context, userFocusTypes);
                return true;
            }
        }
        return false;
    }

    /**
     * 保存用户关注的
     *
     * @param context
     * @param types
     */
    public void saveUserFocusTypes(Context context, ArrayList<CaiPuType.TngouEntity> types) {
        String json = JSONUtils.toJson(types);
        CacheTool.getInstance().saveUserFocusTypes(context, json);
    }

    /**
     * 获取用户关注的
     *
     * @param context
     * @return
     */
    public static ArrayList<CaiPuType.TngouEntity> getUserFocusTypes(Context context) {
        return CacheTool.getUserFocusTypes(context);
    }

    public static void onDestory() {
        instance = null;
        CacheTool.onDestory();
    }
}
