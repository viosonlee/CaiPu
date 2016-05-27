package lee.vioson.caipu.api;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.model.CaipuList;

/**
 * Author:李烽
 * Date:2016-04-29
 * FIXME
 * Todo
 */
public class ApiClient {
    protected static final String IP = "http://apis.baidu.com/tngou/cook/";
    private static final String NAME = "name";
    private static final String SHOW = "show";
    private static final String TAG = "ApiClient";

    private String completeUrl(String dns) {
        return IP + dns;
    }


    private static final String APIKEY = "apikey";

    private static final String APIKEY_VALUE = "3b0f889244f63645afeb76c3af21630f";

    private Map<String, String> getGetApiKey() {
        Map<String, String> map = new HashMap<>();
        map.put(APIKEY, APIKEY_VALUE);
        return map;
    }

    /**
     * 搜索
     *
     * @param keyWord
     * @param resultCallBack
     */
    public void search(String keyWord, HttpTool.ResultCallBack<CaipuList> resultCallBack) {
        String url = completeUrl("name") + "?name=" + keyWord;
        Log.d(TAG, url);
        HttpTool.getAsynWithHeader(url, getGetApiKey(), resultCallBack);
    }

    /**
     * 获取菜谱分类
     *
     * @param resultCallBack
     */
    public void getTypeList(HttpTool.ResultCallBack<CaiPuType> resultCallBack) {
        String url = completeUrl("classify") + "?id=0";
        Log.d(TAG, url);
        HttpTool.getAsynWithHeader(url, getGetApiKey(), resultCallBack);
    }

    /**
     * 分类下菜谱
     *
     * @param id
     * @param page
     * @param rows
     * @param resultCallBack
     */
    public void getCaipuListByType(String id, int page, int rows,
                                   HttpTool.ResultCallBack<CaipuList> resultCallBack) {
        String url = completeUrl("list") + "?id=" + id + "&page=" + page + "&rows=" + rows;
        Log.d(TAG, url);
        HttpTool.getAsynWithHeader(url, getGetApiKey(), resultCallBack);
    }
}
