package lee.vioson.caipu.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import lee.vioson.caipu.R;
import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.comment.Constant;
import lee.vioson.caipu.control.CacheTool;
import lee.vioson.caipu.control.UpDataToBmobTool;
import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.model.Caipu;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.utils.JSONUtils;

/**
 * Author:李烽
 * Date:2016-05-26
 * FIXME
 * Todo https://git.oschina.net/maitailang/Caipu.git
 */
public class AddDataActivity extends BackActivity implements UpDataToBmobTool.UpListener {
    private static final long DELAYED = 1000;
    private static final String TAG = "AddDataActivity";
    private ApiClient client;
    private CaiPuType caiPuType;

    private boolean run = false;//流程控制

    private Handler handler;
    private ArrayList<CaiPuType.TngouEntity> typeData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Bmob.initialize(this, Constant.Bmob_APPLICATION_ID);
        client = new ApiClient();

        String caipuTypes = CacheTool.getCaipuTypes(this);
        caiPuType = JSONUtils.fromJson(caipuTypes, CaiPuType.class);
        typeData = new ArrayList<>();
        handler = new MyHandler();
    }

    public void start(View view) {
        //开始上传数据
        if (caiPuType != null) {
            List<CaiPuType.TngouEntity> tngou = caiPuType.getTngou();
            if (tngou != null) {
                typeData.clear();
                typeData.addAll(tngou);
                upload();
            }
        }

    }

    String type = "";
    int index = 0;
    int page = 1;
    int count = 20;


    private void upload() {
        if (index < typeData.size()) {
            type = typeData.get(index).getId() + "";
            client.getCaipuListByType(type, page, count, new HttpTool.ResultCallBack<CaipuList>() {
                @Override
                protected void onError(Request request, Exception e) {
                    postRun(RETRY);
                }

                @Override
                protected void onResponse(CaipuList caipuList) {
                    if (caipuList.isStatus()) {
                        List<CaipuList.TngouEntity> tngou = caipuList.getTngou();
                        if (tngou != null) {
                            if (tngou.size() > 0) {
                                List<Caipu> caipus = toCaipus(tngou);
                                UpDataToBmobTool.getInstance(AddDataActivity.this)
                                        .upLoad(AddDataActivity.this, caipus);
                            } else postRun(NEXT_TYPE);
                        } else {
                            Log.d(TAG, "tngou is null");
                        }
                    } else {
                        postRun(RETRY);
                    }
                }
            });
        } else {
            handler.sendEmptyMessage(ALL_OK);
        }
    }

    private List<Caipu> toCaipus(List<CaipuList.TngouEntity> data) {
        List<Caipu> caipus = new ArrayList<>();
        for (CaipuList.TngouEntity entity : data) {
            caipus.add(entity.typeFormat());
        }
        return caipus;
    }

    /**
     * 发送
     */
    private void postRun(final int msg) {
        Log.d(TAG, "postRun:" + msg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(msg);
            }
        }, DELAYED);
    }

    private static final int RETRY = 1;
    private static final int NEXT_TYPE = 2;
    private static final int NEXT_PAGE = 4;
    private static final int ALL_OK = 3;

    @Override
    public void onAllOk() {
        postRun(NEXT_PAGE);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEXT_TYPE:
                    index++;
                    page = 1;
                    upload();
                    break;
                case RETRY:
                    upload();
                    break;
                case NEXT_PAGE:
                    page++;
                    upload();
                    break;
                case ALL_OK:
                    index = 1;
                    Log.d(TAG, "all ok");
                    break;
            }
        }
    }
}
