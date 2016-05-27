package lee.vioson.caipu.activities;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.okhttp.Request;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.ListAdapter;
import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.api.HttpTool;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.provider.SearchSuggestionProvider;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public class ResultActivity extends BackActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String KEY_WORD = "key_word";
    private static final String TAG = "ResultActivity";
    private RecyclerView list;
    //    private ProgressDialog dialog;
    private ListAdapter adapter;

    private String keyWord = "";
    private SwipeRefreshLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        rootLayout = (SwipeRefreshLayout) findViewById(R.id.root_layout);
        rootLayout.setOnRefreshListener(this);
        rootLayout.setColorSchemeColors(Color.parseColor("#fadfad"),Color.parseColor("#00dfad"),Color.parseColor("#fadf00"));
        list = (RecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("正在搜索中");
        toSearch();
        keyWord = getKeyWord();
        saveRecord(keyWord);
        setTitle(keyWord);
    }

    private void saveRecord(String keyWord) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY,
                SearchSuggestionProvider.MODE);
        suggestions.saveRecentQuery(keyWord, null);
    }


    private void toSearch() {
        rootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                rootLayout.setRefreshing(true);
            }
        }, 200);
//        dialog.show();
        Log.d(TAG, "toSearch");
        //搜索
        String s = getKeyWord();
        new ApiClient().search(s, new HttpTool.ResultCallBack<CaipuList>() {
            @Override
            protected void onError(Request request, Exception e) {
//                if (dialog != null && dialog.isShowing())
//                    dialog.dismiss();
                rootLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rootLayout.setRefreshing(false);
                    }
                }, 200);
                Log.d(TAG, "onError");
            }

            @Override
            protected void onResponse(CaipuList searchResult) {
//                if (dialog != null && dialog.isShowing())
//                    dialog.dismiss();
                rootLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rootLayout.setRefreshing(false);
                    }
                }, 200);
                Log.d(TAG, "onResponse");
                if (searchResult != null)
                    if (searchResult.getTngou() != null) {
                        adapter = new ListAdapter(searchResult.getTngou(), ResultActivity.this);
                        list.setAdapter(adapter);
                    }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String getKeyWord() {
        return getIntent().getStringExtra(SearchManager.QUERY);
    }

    @Override
    public void onRefresh() {
        toSearch();
    }
}
