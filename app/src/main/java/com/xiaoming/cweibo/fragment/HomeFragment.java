package com.xiaoming.cweibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.activity.ArticleCommentActivity;
import com.xiaoming.cweibo.adapters.HomepageListAdapter;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.presenter.HomePresenter;
import com.xiaoming.cweibo.presenter.HomePresenterImp;
import com.xiaoming.cweibo.views.MVP_view.HomeView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @des 关注微博主页面
 * @author Slience_Manager
 * @time 2017/5/17 13:40
 */

public class HomeFragment extends BaseFragment implements HomeView{

    private RecyclerView.LayoutManager mLayoutManager;
    private PullToRefreshRecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;
    private HomePresenter mHomePresenter;
    private HomepageListAdapter mHomepageListAdapter;
    private List<StatusEntity> mEntityList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mHomePresenter = new HomePresenterImp(this);
        mEntityList = new ArrayList<>();
        mHomepageListAdapter = new HomepageListAdapter(getActivity(),mEntityList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mRecyclerView = (PullToRefreshRecyclerView) inflater.inflate(R.layout.fragment_home,container,false);
        init();
        mHomePresenter.loadData(true);
        return mRecyclerView;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(String tag){
        switch (tag){
            case "HOME":
                mHomePresenter.requestPublicTimeLine();
                break;
            case "ME":
                mHomePresenter.requestUserTimeLine();
                break;
            case "REPOST_FINISH":
                mHomePresenter.requestUserTimeLine();
                break;
            case "COMMENTS_FINISH":
                mHomePresenter.requestUserTimeLine();
                break;
        }
    }

    private void init() {
        //默认为vertical
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //自定义recyclerView的分割线
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        //添加分割线
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setAdapter(mHomepageListAdapter);
        mHomepageListAdapter.setOnItemClickListener(new HomepageListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ArticleCommentActivity.class);
                intent.putExtra(StatusEntity.class.getSimpleName(),mEntityList.get(position));
                startActivity(intent);
            }
        });
        //改变模式才能做上拉
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mHomePresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mHomePresenter.loadMore(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefreshComplete(List<StatusEntity> list,boolean loadMore) {
        mRecyclerView.onRefreshComplete();
        if(list!=null&&list.size()>0){
            if(!loadMore){
                mEntityList.clear();
            }
            mEntityList.addAll(list);
            mHomepageListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        mRecyclerView.onRefreshComplete();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

}
