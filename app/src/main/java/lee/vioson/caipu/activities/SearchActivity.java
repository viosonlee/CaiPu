package lee.vioson.caipu.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import lee.vioson.caipu.R;
import lee.vioson.caipu.provider.SearchSuggestionProvider;

/**
 * Author:李烽
 * Date:2016-05-03
 * FIXME
 * Todo
 */
@Deprecated
public class SearchActivity extends AppCompatActivity {
    private String query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = mSearchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        return true;
    }

    public void click(View v) {
        onSearchRequested();
    }

    @Override
    public boolean onSearchRequested() {
        //打开浮动搜索框（第一个参数默认添加到搜索框的值）
        startSearch(null, false, null, false);
        return true;
    }


    //得到搜索结果
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //获得搜索框里值
        query = intent.getStringExtra(SearchManager.QUERY);
        System.out.println(query);
        //保存搜索记录
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
        System.out.println("保存成功");
    }
}
