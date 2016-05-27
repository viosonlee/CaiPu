package lee.vioson.caipu.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import lee.vioson.caipu.R;

/**
 * Author:李烽
 * Date:2016-05-23
 * FIXME
 * Todo 才谱详情
 */
public class DescriptionActivity extends BackActivity {

    public static final String CONTENT = "message";
    public static final String TITLE = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
//                , ViewGroup.LayoutParams.MATCH_PARENT);
//        layoutParams.setMargins(10, 10, 10, 10);
//        TextView textView = new TextView(this);
        setContentView(R.layout.activity_description);
        TextView contentText = (TextView) findViewById(R.id.content);
        String content = getIntent().getStringExtra(CONTENT);
        contentText.setText(Html.fromHtml(content));
        String title = getIntent().getStringExtra(TITLE);
        setTitle(title + "的做法");
    }
}
