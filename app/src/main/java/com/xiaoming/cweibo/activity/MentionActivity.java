package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.adapters.MentionAdapter;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.presenter.BasePresenter;
import com.xiaoming.cweibo.presenter.MentionPresenterImp;
import com.xiaoming.cweibo.utils.DividerItemDecoration;
import com.xiaoming.cweibo.views.MVP_view.HomeView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2411:37
 */

public class MentionActivity extends BaseActivity implements HomeView{

    private RecyclerView.LayoutManager mLayoutManager;
    private PullToRefreshRecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<StatusEntity> mEntityList;
    private MentionAdapter mMentionAdapter;
    private BasePresenter mBasePresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.at_me);
        mEntityList = new ArrayList<>();
        init();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mention;
    }

    private void init() {
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //分割线
        mItemDecoration = new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mMentionAdapter = new MentionAdapter(getActivity(),mEntityList);
        mRecyclerView.setAdapter(mMentionAdapter);
        mMentionAdapter.setOnItemClickListener(new MentionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {

            }
        });
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mBasePresenter = new MentionPresenterImp(this);
        mBasePresenter.loadData(true);

    }

    @Override
    public void onRefreshComplete(List<StatusEntity> list,boolean loadMore) {
        mRecyclerView.onRefreshComplete();
        if(list!=null&&list.size()>0){
            if(!loadMore){
                mEntityList.clear();
            }
            mEntityList.addAll(list);
            mMentionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        mRecyclerView.onRefreshComplete();
    }
}
