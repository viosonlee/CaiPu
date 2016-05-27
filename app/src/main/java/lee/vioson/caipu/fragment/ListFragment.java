package lee.vioson.caipu.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import lee.vioson.caipu.R;
import lee.vioson.caipu.adapter.ListAdapter;
import lee.vioson.caipu.api.ApiClient;
import lee.vioson.caipu.callback.RequestDataNotCache;
import lee.vioson.caipu.control.CaipuListHelper;
import lee.vioson.caipu.model.CaipuList;
import lee.vioson.caipu.views.UpLoadRecyclerView;

/**
 * Author:李烽
 * Date:2016-05-09
 * FIXME
 * Todo
 */
public class ListFragment extends Fragment implements UpLoadRecyclerView.OnLoadMoreCallBack, SwipeRefreshLayout.OnRefreshListener {
    private static final int DEFALUT_PAGE = 1;
    private static final String TYPE = "type";
    UpLoadRecyclerView mRecyclerView;
    ArrayList<CaipuList.TngouEntity> mData;
    private ListAdapter baseRecyclerAdapter;
    private int page = DEFALUT_PAGE;
    ApiClient client;
    String type = "0";
    private SwipeRefreshLayout rootLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.list_fragment, container, false);
        rootLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.root_layout);
        mRecyclerView = (UpLoadRecyclerView) inflate.findViewById(R.id.list);
        return inflate;
    }

    public static ListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getString(TYPE);
        mData = new ArrayList<>();
        client = new ApiClient();

        mRecyclerView.setOnLoadMoreCallBack(this);

        rootLayout.setOnRefreshListener(this);
        rootLayout.setColorSchemeColors(Color.parseColor("#fadfad"), Color.parseColor("#00dfad"), Color.parseColor("#fadf00"));
        rootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                rootLayout.setRefreshing(true);
            }
        }, 500);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        baseRecyclerAdapter = new ListAdapter(mData, getActivity());

        mRecyclerView.setAdapter(baseRecyclerAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(true);
    }

    public void upDate(String type) {
        this.type = type;
        loadData(true);
    }

    /**
     * 加载数据
     */
    private void loadData(final boolean isRefresh) {
        CaipuListHelper.getInstance().getCaipuList(type, page, 15, getActivity(), new RequestDataNotCache<CaipuList>() {
            @Override
            protected void onSuccess(CaipuList data) {
                if (isRefresh)
                    mData.clear();
                mData.addAll(data.getTngou());
                baseRecyclerAdapter.notifyDataSetChanged();
                if (!isRefresh && data.getTngou().size() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.no_more), Toast.LENGTH_SHORT).show();
                    mRecyclerView.setHasMore(false);
                }
            }

            @Override
            protected void onFailure(String msg) {

            }

            @Override
            protected void onFinish() {
                mRecyclerView.loadComplete();
                rootLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rootLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

    }

    @Override
    public void onLoadMore() {
        page++;
        loadData(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CaipuListHelper.onDestory();
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
