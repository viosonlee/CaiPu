package lee.vioson.caipu.control;

import android.content.Context;

import com.squareup.okhttp.Request;

import java.util.List;

import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.callback.OnQueryHandler;
import lee.vioson.caipu.callback.RequestDataNotCache;
import lee.vioson.caipu.model.CaipuList;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public class CaipuListHelper {
    private static final String TAG = "CaipuTypeHelper";
    private static CaipuListHelper instance;

    private ApiClient client;

    private CaipuListHelper() {
        client = new ApiClient();
    }

    public static synchronized CaipuListHelper getInstance() {
        if (instance == null)
            synchronized (CaipuListHelper.class) {
                if (instance == null)
                    instance = new CaipuListHelper();
            }
        return instance;
    }

    /**
     * 获得菜谱
     *
     * @param type
     * @param page
     * @param count
     * @param context
     * @param handler
     */
    public void getCaipuList(String type, int page, int count, final Context context, final RequestDataNotCache<CaipuList> handler) {
        client.getCaipuListByType(type, page, count, new HttpTool.ResultCallBack<CaipuList>() {
            @Override
            protected void onError(Request request, Exception e) {
                handler.onRequestFailture(request.toString());
                handler.onRequestFinish();
            }

            @Override
            protected void onResponse(final CaipuList caipuList) {
                if (caipuList != null)
                    CaipulistContorller.getInstance().matchLikeCaipu(context, caipuList.getTngou(),
                            new OnQueryHandler<List<CaipuList.TngouEntity>>() {
                                @Override
                                public void onDataBack(List<CaipuList.TngouEntity> tngouEntities) {
                                    caipuList.setTngou(tngouEntities);
                                    handler.onRequestSuccess(caipuList);
                                    handler.onRequestFinish();
                                }
                            });
                else {
                    handler.onRequestFailture(OnQueryHandler.RESULT_EMPTY);
                    handler.onRequestFinish();
                }
            }
        });
    }

    public static void onDestory() {
        instance = null;
    }
}
