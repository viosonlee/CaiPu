package lee.vioson.caipu.activities;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import lee.vioson.caipu.R;
import lee.vioson.caipu.callback.RequestDataNotCache;
import lee.vioson.caipu.control.CacheTool;
import lee.vioson.caipu.control.CaipuTypeHelper;
import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.utils.ActivitySwitchBase;

/**
 * Author:李烽
 * Date:2016-05-25
 * FIXME
 * Todo
 */
public class LaunchActivity extends FillStatusBarActivity implements Animation.AnimationListener {

    private static final long ANIMATION_DURATION = 1000;
    private boolean otherOk = false;
    private boolean firstOpen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(ANIMATION_DURATION);
        rootLayout.setAnimation(alphaAnimation);
        alphaAnimation.start();
        alphaAnimation.setAnimationListener(this);
        loadType();
    }

    private void loadType() {
        firstOpen = CacheTool.isFirstOpen(this);
        if (firstOpen)
            CaipuTypeHelper.getInstance().getCaipuTypes(this, new RequestDataNotCache<CaiPuType>() {
                @Override
                protected void onSuccess(CaiPuType data) {
                    CacheTool.getInstance().saveCaipuTypes(LaunchActivity.this, data);
                    if (otherOk)
                        toNextActivity();
                    else otherOk = true;
                }

                @Override
                protected void onFailure(String msg) {
                    loadType();
                }

                @Override
                protected void onFinish() {

                }

            });
        else otherOk = true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (otherOk)
            toNextActivity();
        else otherOk = true;
    }

    private void toNextActivity() {
        if (firstOpen)
            ActivitySwitchBase.toChooseType(this);
        else ActivitySwitchBase.toMain(this);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CaipuTypeHelper.onDestory();
        CacheTool.onDestory();
    }
}
