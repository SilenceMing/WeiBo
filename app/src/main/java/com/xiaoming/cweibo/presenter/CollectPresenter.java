package com.xiaoming.cweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.entities.FavoritesEntity;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.CollectView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2416:31
 */

public class CollectPresenter implements BasePresenter {
    private int PAGE = 1;
    private CollectView mCollectView;
    private WeiboParameters mParameters;

    public CollectPresenter(CollectView collectView) {
        mCollectView = collectView;
        mParameters = new WeiboParameters(Constant.APP_KEY);
    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 1;
        LoadCollectData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++;
        LoadCollectData(true,showLoading);
    }

    private void LoadCollectData(final boolean isLoadMore, final boolean showLoading) {
        new BaseNetWork(mCollectView, Urls.FAVORITES, showLoading) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mCollectView.getActivity()).getToken());
                mParameters.put(ParameterKey.PAGE,PAGE);
                mParameters.put(ParameterKey.COUNT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                List<FavoritesEntity> list;
                Type type = new TypeToken<List<FavoritesEntity>>(){}.getType();
                list = new Gson().fromJson(response.response,type);
                mCollectView.onRefreshComplete(list,isLoadMore);
            }
        }.get();
    }
}
