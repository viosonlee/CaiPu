package lee.vioson.caipu.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import java.util.ArrayList;

import lee.vioson.caipu.R;
import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.base.CommonAdapter;
import lee.vioson.caipu.base.ViewHolder;
import lee.vioson.caipu.fragment.ListFragment;
import lee.vioson.caipu.model.CaiPuType;

/**
 * Author:李烽
 * Date:2016-05-09
 * FIXME
 * Todo
 */
@Deprecated
public class ListActivity extends AppCompatActivity {
    private ArrayList<CaiPuType.TngouEntity> mTypes;
    private PopupWindow popupWindow;
    private ListView mListView;
    ApiClient client;
    ListFragment fragment;
    private int actionbarHeight = 0;
    private ProgressDialog dialog;
    private View inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mTypes = new ArrayList<>();
        inflate = LayoutInflater.from(this).inflate(R.layout.menu_list, null);
        mListView = (ListView) inflate.findViewById(R.id.list);
        mListView.setBackgroundColor(Color.parseColor("#ffffff"));
        client = new ApiClient();
        fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        initPop();
        loadTypes();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void loadTypes() {
        client.getTypeList(new HttpTool.ResultCallBack<CaiPuType>() {
            @Override
            protected void onError(Request request, Exception e) {
                Toast.makeText(ListActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onResponse(CaiPuType caiPuType) {
                mListView.setAdapter(new CommonAdapter<CaiPuType.TngouEntity>
                        (ListActivity.this, caiPuType.getTngou(), R.layout.simple_list_item) {
                    @Override
                    public void convert(ViewHolder helper, final CaiPuType.TngouEntity item, int position) {
                        helper.setText(R.id.name_text, item.getName());
                        helper.getView(R.id.root_view).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragment.upDate(item.getId() + "");
                                if (popupWindow != null)
                                    popupWindow.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    private void initPop() {
        popupWindow = new PopupWindow();

        popupWindow.setContentView(inflate);
//        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(300);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_type_list) {
            showPop();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPop() {
        actionbarHeight = getSupportActionBar().getHeight();
        popupWindow.showAtLocation(mListView, Gravity.TOP|Gravity.END, 100, 200);
    }
}
