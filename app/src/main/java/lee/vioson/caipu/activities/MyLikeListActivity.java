package lee.vioson.caipu.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.ListAdapter;
import lee.vioson.caipu.callback.OnQueryHandler;
import lee.vioson.caipu.control.CaipulistContorller;
import lee.vioson.caipu.model.CaipuList;

/**
 * Author:李烽
 * Date:2016-05-21
 * FIXME
 * Todo 我的收藏
 */

public class MyLikeListActivity extends BackActivity {
    private RecyclerView list;
    private ArrayList<CaipuList.TngouEntity> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_list);
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        ListAdapter adapter = new ListAdapter(mData, this);
        list.setAdapter(adapter);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        CaipulistContorller.getInstance().getAllDatas(this.getApplicationContext(),
                new OnQueryHandler<ArrayList<CaipuList.TngouEntity>>() {
                    @Override
                    public void onDataBack(ArrayList<CaipuList.TngouEntity> tngouEntities) {
                        initList(tngouEntities);
                    }
                });
    }

    private void initList(ArrayList<CaipuList.TngouEntity> tngouEntities) {
        Collections.reverse(tngouEntities);
        mData.clear();
        mData.addAll(tngouEntities);
        list.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CaipulistContorller.onDestroy();
    }
}
