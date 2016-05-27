package lee.vioson.caipu.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Author:李烽
 * Date:2016-05-06
 * FIXME
 * Todo 上拉加载更多的 RecyclerView
 */
public class UpLoadRecyclerView extends RecyclerView {

    private static boolean isLoading = false;

    public  void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    private  boolean hasMore = true;


    public UpLoadRecyclerView(Context context) {
        this(context, null);
    }

    private ProgressDialog dialog;

    private String dialogMessage = "正在加载更多";

    public UpLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(dialogMessage);
        this.addOnScrollListener(new OnScrollListener() {
            boolean isSlidingToLast;
            LinearLayoutManager layoutManager;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //底部上拉
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //当不滚动的时候
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                        int itemCount = layoutManager.getItemCount();
                        if (lastCompletelyVisibleItemPosition == (itemCount - 1) && isSlidingToLast) {
                            if (onLoadMoreCallBack != null)
                                if (!isLoading && hasMore) {
                                    onLoadMoreCallBack.onLoadMore();
                                    isLoading = true;
                                    dialog.show();
                                }
                        }
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    Log.e("ClassCastException", "layoutManager 必须是LinearLayoutManager");
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }

    public void setOnLoadMoreCallBack(OnLoadMoreCallBack onLoadMoreCallBack) {
        this.onLoadMoreCallBack = onLoadMoreCallBack;
    }

    private OnLoadMoreCallBack onLoadMoreCallBack;

    public void loadComplete() {
        isLoading = false;
        dialog.dismiss();
    }

    public interface OnLoadMoreCallBack {

        void onLoadMore();

    }
}
