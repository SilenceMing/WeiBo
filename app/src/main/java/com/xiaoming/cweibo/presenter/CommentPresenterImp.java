package com.xiaoming.cweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.entities.CommentEntity;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.CommentView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2415:02
 */

public class CommentPresenterImp implements BasePresenter {
    private int PAGE = 1;
    private CommentView mCommentView;
    private WeiboParameters mParameters;

    public CommentPresenterImp(CommentView commentView) {
        mCommentView = commentView;
        mParameters = new WeiboParameters(Constant.APP_KEY);
    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 1;
        LoadComment(false, showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++;
        LoadComment(true, showLoading);
    }

    private void LoadComment(final boolean isLoadMore, final boolean showLoading) {
        new BaseNetWork(mCommentView, Urls.BY_ME, showLoading) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mCommentView.getActivity()).getToken());
                mParameters.put(ParameterKey.PAGE,PAGE);
                mParameters.put(ParameterKey.COUNT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                List<CommentEntity> list;
                Type type = new TypeToken<List<CommentEntity>>() {
                }.getType();
                list = new Gson().fromJson(response.response, type);
                mCommentView.onRefreshComplete(list, isLoadMore);
            }
        }.get();
    }
}
