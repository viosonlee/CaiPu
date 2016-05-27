package lee.vioson.caipu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lee.vioson.caipu.control.CaipulistContorller;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.views.CaipuItem;

/**
 * Author:李烽
 * Date:2016-05-03
 * FIXME
 * Todo
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CaipuList.TngouEntity> mData;
    private Context mContext;

    public ListAdapter(final List<CaipuList.TngouEntity> data, Context mContext) {
        mData = new ArrayList<>();
        mData = data;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        CaipuItem caipuItem = new CaipuItem(mContext);
        return new ViewHolder(caipuItem);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CaipuList.TngouEntity data = mData.get(position);
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.item.addData(data);
        holder1.item.setOnLikeListener(new CaipuItem.OnLikeListener() {
            @Override
            public void onLikeChange(View v, boolean isLiked) {
                Toast.makeText(mContext, isLiked ? "收藏成功" : "取消收藏", Toast.LENGTH_SHORT).show();
                data.like(isLiked);
                mData.set(position, data);
                if (isLiked)
                    CaipulistContorller.getInstance().addDataNotRepeat(mContext.getApplicationContext(), data);
                else
                    CaipulistContorller.getInstance().remove(mContext.getApplicationContext(), data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CaipuItem item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (CaipuItem) itemView;

        }
    }


}
