package lee.vioson.caipu.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Author:李烽
 * Date:2016-05-26
 * FIXME
 * Todo 上传数据到Bmob服务器上去
 */
public class UpDataToBmobTool {

    private static final String TAG = "UpDataToBmobTool";
    private static final int RETRY = 1;
    private static final int NEXT = 2;
    private static final int ALL_OK = 3;
    private static int index = 0;

    private List<BmobObject> mData;

    private Context mContext;

    public UpListener listener;

    private static UpDataToBmobTool instance;

    private UpDataToBmobTool(UpListener listener) {
        this.listener = listener;
    }

    public static UpDataToBmobTool getInstance(UpListener listener) {
        if (instance == null)
            synchronized (UpDataToBmobTool.class) {
                if (instance == null)
                    instance = new UpDataToBmobTool(listener);
            }
        return instance;
    }

    /**
     * 上传数据
     *
     * @param context
     * @param data
     */
    public void upLoad(Context context, List<? extends BmobObject> data) {
        mData = new ArrayList<>();
        mData.clear();
        mData.addAll(data);
        mContext = context;
        if (index < data.size()) {
            upLoadData(context, data.get(index));
        } else {
            handler.sendEmptyMessage(ALL_OK);
        }
    }

    private void upLoadData(Context context, BmobObject data) {
        if (data == null)
            return;
        data.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                handler.sendEmptyMessage(NEXT);
                Log.d(TAG, "upLoad onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.d(TAG, "upLoad onFailure:" + s);
                handler.sendEmptyMessage(RETRY);
            }
        });

    }

    private UpHandler handler = new UpHandler();


    @SuppressLint("HandlerLeak")
    class UpHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NEXT:
                    index++;
                    upLoad(mContext, mData);
                    break;
                case RETRY:
                    upLoad(mContext, mData);
                    break;
                case ALL_OK:
                    index = 1;
                    if (listener != null)
                        listener.onAllOk();
                    break;
            }
        }

    }

    /**
     * 回调
     */
    public interface UpListener {
        void onAllOk();
    }

}
