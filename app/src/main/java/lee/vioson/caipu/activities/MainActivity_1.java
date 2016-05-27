package lee.vioson.caipu.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.PagerAdapter;
import lee.vioson.caipu.control.CacheTool;
import lee.vioson.caipu.control.CaipuTypeHelper;
import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.utils.ActivitySwitchBase;

/**
 * Author:李烽
 * Date:2016-05-16
 * FIXME
 * Todo
 */
public class MainActivity_1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> tabTitles;
    private ArrayList<String> types;
    private DrawerLayout drawer;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_1);
        Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
//        client = new ApiClient();
//        dialog = new ProgressDialog(this);
        tabTitles = new ArrayList<>();
        types = new ArrayList<>();

        if (CacheTool.isFirstOpen(this))
            CacheTool.getInstance().saveFirstOpen(this, false);//记录不是第一次进入

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


        loadTabs();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("请输入完整菜名");
//        searchView.setSuggestionsAdapter(new SuggestAdapter(this, getCursor()));
//        searchView.setOnSuggestionListener(this);
//        searchView.setOnQueryTextListener(this);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setQueryRefinementEnabled(true);
        Log.d("searchView", searchView.getChildCount() + "");
        LinearLayout linearLayout = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(2);
        LinearLayout layout = (LinearLayout) linearLayout1.getChildAt(1);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) layout.getChildAt(0);
        searchAutoComplete.setDropDownBackgroundResource(R.drawable.bg_history);
        searchAutoComplete.setHintTextColor(Color.parseColor("#ffffff"));
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.support.v4.view.PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null)
            adapter.notifyDataSetChanged();
        if (searchView != null) {
            searchView.setIconified(true);
        }
//        loadTabs();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadTabs();
    }

    private void loadTabs() {
        ArrayList<CaiPuType.TngouEntity> userFocusTypes = CaipuTypeHelper.getUserFocusTypes(this);
        if (userFocusTypes != null) {
            updateView(userFocusTypes);
        } else {
            ActivitySwitchBase.toChooseType(this);
        }
//        else
//            CaipuTypeHelper.getInstance().getCaipuTypes(this, new RequestDataHandler<CaiPuType>() {
//                @Override
//                public void onCacheData(boolean isEmpty, CaiPuType caiPuType) {
//                    if (isEmpty)
//                        return;
//                    updateView(caiPuType.getTngou());
//                }
//
//                @Override
//                public void onRequestSuccess(CaiPuType caiPuType) {
//                    updateView(caiPuType.getTngou());
//                }
//
//                @Override
//                public void onRequestFailture(String msg) {
//                    Log.e(TAG, msg);
//                }
//
//                @Override
//                public void onRequestFinish() {
//                }
//            });

    }

    private void updateView(List<CaiPuType.TngouEntity> tngou) {
        tabTitles.clear();
        types.clear();
        for (int i = 0; i < tngou.size(); i++) {
            String title = tngou.get(i).getName() + "类";
            tabLayout.addTab(tabLayout.newTab().setText(title));
            tabTitles.add(title);
            types.add(tngou.get(i).getId() + "");
        }
        if (types.size() > 4)
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        else tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), types, tabTitles));
        tabLayout.setupWithViewPager(viewPager);
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        //提交搜索
//        SearchHistoryController.getInstance().addData(this, query);
//        ActivitySwitchBase.toSearchResult(this, query);
//        return false;
//    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
//
//    public Cursor getCursor() {
//        return SearchHistoryController.getInstance().getCursor(this);
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SearchHistoryController.onDestroy();
//        SearchHistoryController.getInstance().getCursor(this).close();
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                ActivitySwitchBase.toMyLikeList(this);
                break;
            case R.id.type:
                ActivitySwitchBase.toChooseType(this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CaipuTypeHelper.onDestory();
        CacheTool.onDestory();
    }
//    @Override
//    public boolean onSuggestionSelect(int position) {
//        return false;
//    }

//    @Override
//    public boolean onSuggestionClick(int position) {
//        Cursor cursor = searchView.getSuggestionsAdapter().getCursor();
//        searchView.setQuery(cursor.getString(cursor.getColumnIndex(SearchHistoryDBHelper.KEY_WORD)), true);
//        return false;
//    }
}
