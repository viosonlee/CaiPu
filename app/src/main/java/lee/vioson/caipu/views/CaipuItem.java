package lee.vioson.caipu.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lee.vioson.caipu.R;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.utils.ActivitySwitchBase;
import lee.vioson.caipu.utils.UrlUtil;

/**
 * Author:李烽
 * Date:2016-05-20
 * FIXME
 * Todo
 */
public class CaipuItem extends LinearLayout {
    private static final String TAG = "CaipuItem";
    private CardView cardview;
    private ImageView icon;
    private TextView title;
    private TextView food;
    private TextView message;
    private ImageView likeBtn;

    private boolean isLike = false;

    public void setOnLikeListener(OnLikeListener onLikeListener) {
        this.onLikeListener = onLikeListener;
    }

    private OnLikeListener onLikeListener;

    public CaipuItem(Context context) {
        this(context, null);
    }

    public CaipuItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaipuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_view, this);
        cardview = (CardView) findViewById(R.id.cardview);
        icon = (ImageView) findViewById(R.id.icon);
        title = (TextView) findViewById(R.id.title);
        food = (TextView) findViewById(R.id.food);
        message = (TextView) findViewById(R.id.message);
        likeBtn = (ImageView) findViewById(R.id.like_btn);
    }

    public interface OnLikeListener {
        void onLikeChange(View v, boolean isLiked);
    }


    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(final CaipuList.TngouEntity data) {
        Log.d(TAG, data.toString());
        title.setText(data.getName());
        food.setText(data.getFood());
        message.setText(Html.fromHtml(data.getDescription()));
//        likeBtn.setChecked(data.isLike());
        isLike = data.isLike();
        likeBtn.setImageResource(isLike ? R.drawable.like : R.drawable.not_like);
        Log.i(TAG, data.getLike() + "收藏");


        likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                togle();
                if (onLikeListener != null)
                    onLikeListener.onLikeChange(likeBtn, isLike);
            }
        });
        Picasso.with(getContext()).load(UrlUtil.completeImgUrlWithSize(data.getImg(), 160, 160))
//                .fit()
                .placeholder(R.drawable.pic_icon)
                .error(R.drawable.pic_icon)
                .into(icon);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getMessage()))
                    ActivitySwitchBase.toH5Activity(getContext().getApplicationContext(), data.getName()
                            , UrlUtil.completeDetailUrl(data.getId() + ""));
                else
                    ActivitySwitchBase.toDescription(getContext(), data.getMessage(), data.getName());

            }
        });
    }


    private void togle() {
        isLike = !isLike;
        likeBtn.setImageResource(isLike ? R.drawable.like : R.drawable.not_like);
    }

}
