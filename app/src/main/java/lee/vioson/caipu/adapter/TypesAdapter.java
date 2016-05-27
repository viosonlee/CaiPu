package lee.vioson.caipu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import lee.vioson.caipu.R;
import lee.vioson.caipu.model.CaiPuType;

/**
 * Author:李烽
 * Date:2016-05-23
 * FIXME
 * Todo
 */
public class TypesAdapter extends BaseAdapter {
    public final ArrayList<CaiPuType.TngouEntity> temp;
    private Context mContext;

    private ArrayList<CaiPuType.TngouEntity> mData;

    public TypesAdapter(Context mContext, ArrayList<CaiPuType.TngouEntity> mData) {
        this.temp = new ArrayList<>();
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.type_label, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        boolean isChecked = checkStatus(position);
        holder.textView = (CheckBox) convertView.findViewById(R.id.label);
        holder.textView.setChecked(isChecked);
        if (isChecked) {
            holder.textView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.textView.setTextColor(Color.parseColor("#333333"));
        }
        holder.textView.setText(mData.get(position).getName());
        holder.textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckChangeListener != null)
                    onCheckChangeListener.onCheckedChanged(mData.get(position), isChecked);
                if (isChecked) {
                    holder.textView.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    holder.textView.setTextColor(Color.parseColor("#333333"));
                }
            }
        });
        return convertView;
    }

    private boolean checkStatus(int position) {
        for (int i = 0; i < temp.size(); i++) {
            CaiPuType.TngouEntity tngouEntity = temp.get(i);
            if (tngouEntity.getId() == mData.get(position).getId())
                return true;
        }
        return false;
    }

    public void setOnCheckChangeListener(TypesAdapter.onCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    private onCheckChangeListener onCheckChangeListener;

    public interface onCheckChangeListener {
        void onCheckedChanged(CaiPuType.TngouEntity tngouEntity, boolean isChecked);
    }

    class ViewHolder {
        private CheckBox textView;
    }
}
