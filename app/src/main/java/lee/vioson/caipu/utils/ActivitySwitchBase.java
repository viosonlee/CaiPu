package lee.vioson.caipu.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import lee.vioson.caipu.activities.ChooseTypeActivity;
import lee.vioson.caipu.activities.DescriptionActivity;
import lee.vioson.caipu.activities.MainActivity_1;
import lee.vioson.caipu.activities.MyLikeListActivity;
import lee.vioson.caipu.activities.ResultActivity;
import lee.vioson.caipu.activities.WebActivity;

/**
 * Author:李烽
 * Date:2016-05-03
 * FIXME
 * Todo
 */
public class ActivitySwitchBase {
    public static final String DATA = "data";

    /**
     * 跳转到H5页面
     *
     * @param context
     * @param title
     * @param URL
     */
    public static void toH5Activity(Context context, String title, String URL) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.TITLE, title);
        bundle.putString(WebActivity.URL, URL);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 去搜索页面
     *
     * @param context
     * @param keyWord
     */
    public static void toSearchResult(Context context, String keyWord) {
        Intent intent = new Intent(context, ResultActivity.class)
                .putExtra(ResultActivity.KEY_WORD, keyWord);
        context.startActivity(intent);
    }

    /**
     * 去搜索页面
     *
     * @param context
     */
    public static void toSearchResult(Context context) {
        Intent intent = new Intent(context, ResultActivity.class);
        context.startActivity(intent);
    }

    /**
     * 去我的收藏菜单
     *
     * @param context
     */
    public static void toMyLikeList(Context context) {
        context.startActivity(new Intent(context, MyLikeListActivity.class));
    }

    public static void toDescription(Context context, String message, String name) {
        context.startActivity(new Intent(context, DescriptionActivity.class)
                .putExtra(DescriptionActivity.CONTENT, message).putExtra(DescriptionActivity.TITLE, name));
    }

    /**
     * 去首页
     *
     * @param context
     */
    public static void toMain(Context context) {
        context.startActivity(new Intent(context, MainActivity_1.class));
    }

    /**
     * 去选择类型
     *
     * @param context
     */
    public static void toChooseType(Context context) {
        context.startActivity(new Intent(context, ChooseTypeActivity.class));
    }
}
