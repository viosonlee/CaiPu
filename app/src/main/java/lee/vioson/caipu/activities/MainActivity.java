package lee.vioson.caipu.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.ListAdapter;
import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.model.CaipuList;
@Deprecated
public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private static final String TAG = "MainActivity";
    private EditText inputsearch;
    private RecyclerView list;
    private ListAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }


    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载中");
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        inputsearch = (EditText) findViewById(R.id.input_search);
    }

    private void initEvent() {
        inputsearch.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            toSearch();
            return true;
        }
        return false;
    }



    private void toSearch() {
        inputsearch.clearFocus();
        dialog.show();
        Log.d(TAG, "toSearch");
        //搜索
        String s = inputsearch.getText().toString();
        new ApiClient().search(s, new HttpTool.ResultCallBack<CaipuList>() {
            @Override
            protected void onError(Request request, Exception e) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Log.d(TAG, "onError");
            }

            @Override
            protected void onResponse(CaipuList searchResult) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Log.d(TAG, "onResponse");
                if (searchResult != null)
                    if (searchResult.getTngou() != null) {
                        adapter = new ListAdapter(searchResult.getTngou(), MainActivity.this);
                        list.setAdapter(adapter);
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.addSubMenu(0, 2, 0, "分类查看");
        getMenuInflater().inflate(R.menu.menu_main_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_type) {
            startActivity(new Intent(this, ListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
