package lee.vioson.caipu.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.TypesAdapter;
import lee.vioson.caipu.callback.CacheDataHandler;
import lee.vioson.caipu.callback.RequestDataNotCache;
import lee.vioson.caipu.control.CacheTool;
import lee.vioson.caipu.control.CaipuTypeHelper;
import lee.vioson.caipu.model.CaiPuType;
import lee.vioson.caipu.utils.ActivitySwitchBase;
import lee.vioson.caipu.views.FlowLayout;

/**
 * Author:李烽
 * Date:2016-05-23
 * FIXME
 * Todo 选择分类的
 */
public class ChooseTypeActivity extends BackActivity implements TypesAdapter.onCheckChangeListener {
    private FlowLayout flowLayout;
    private boolean isReload = false;
    private Button btn;
    //    private ArrayList<CaiPuType.TngouEntity> temp = new ArrayList<>();//缓存
    private ArrayList<CaiPuType.TngouEntity> types = new ArrayList<>();

    private TypesAdapter typesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        btn = (Button) findViewById(R.id.btn);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        typesAdapter = new TypesAdapter(this, types);
        flowLayout.setAdapter(typesAdapter);
        typesAdapter.setOnCheckChangeListener(this);
        loadData();
    }

    private void loadData() {
        btn.setEnabled(false);
        ArrayList<CaiPuType.TngouEntity> userFocusTypes = CacheTool.getUserFocusTypes(this);
        typesAdapter.temp.clear();
        typesAdapter.temp.addAll(userFocusTypes);
        checkBtnEnable();
        CaipuTypeHelper.getInstance().getCaipuTypeInCache(this, new CacheDataHandler<CaiPuType>() {
            @Override
            public void onCacheDataBack(boolean isEmpty, CaiPuType data) {
                if (!isEmpty) {
                    List<CaiPuType.TngouEntity> tngou = data.getTngou();
                    types.clear();
                    types.addAll(tngou);
                    typesAdapter.notifyDataSetChanged();
                    flowLayout.notifyData();
                    isReload = false;
                } else {
                    loadWebData();
                }
            }
        });
    }

    private void loadWebData() {
        btn.setEnabled(false);
        CaipuTypeHelper.getInstance().getCaipuTypes(this, new RequestDataNotCache<CaiPuType>() {
            @Override
            protected void onSuccess(CaiPuType data) {
                if (data != null) {
                    if (data.getTngou() != null) {
                        List<CaiPuType.TngouEntity> tngou = data.getTngou();
                        types.clear();
                        types.addAll(tngou);
                        typesAdapter.notifyDataSetChanged();
                        flowLayout.notifyData();
                        isReload = false;
                        btn.setEnabled(true);
                    } else reload();
                } else reload();
            }

            @Override
            protected void onFailure(String msg) {
                reload();
            }

            @Override
            protected void onFinish() {

            }

        });
    }

    private void reload() {
        isReload = true;
        Toast.makeText(ChooseTypeActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
        btn.setText(R.string.reload);
        btn.setEnabled(true);
    }

    public void click(View v) {
        if (!isReload) {
            CaipuTypeHelper.getInstance().saveUserFocusTypes(this, typesAdapter.temp);
            //去主页
//            if (CacheTool.isFirstOpen(this))
            ActivitySwitchBase.toMain(this);
            finish();
        } else {
            btn.setEnabled(false);
            btn.setText(R.string.start_use);
            loadWebData();
        }
    }

    @Override
    public void onCheckedChanged(CaiPuType.TngouEntity tngouEntity, boolean isChecked) {
        if (isChecked)
            typesAdapter.temp.add(tngouEntity);
        else {
            removeItem(tngouEntity);
        }
//        typesAdapter.temp.remove(tngouEntity);

        Log.d("temp", typesAdapter.temp.size() + "");
        checkBtnEnable();
    }

    private void removeItem(CaiPuType.TngouEntity tngouEntity) {
        for (int i = 0; i < typesAdapter.temp.size(); i++) {
            if (typesAdapter.temp.get(i).getId() == tngouEntity.getId())
                typesAdapter.temp.remove(i);
        }
    }

    private void checkBtnEnable() {
        if (typesAdapter.temp.size() > 0) {
            btn.setEnabled(true);
        } else btn.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CaipuTypeHelper.onDestory();
        CacheTool.onDestory();
    }
}
