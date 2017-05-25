package com.xiaoming.cweibo.presenter;

import android.content.Intent;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.activity.LanddingPageActivity;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.entities.UserEntity;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.ProfileView;

/**
 * @author slience
 * @des
 * @time 2017/5/2418:01
 */

public class ProfilePresenterImp implements ProfilePresenter {

    private WeiboParameters mParameters;
    private ProfileView mProfileView;

    public ProfilePresenterImp(ProfileView profileView) {
        mProfileView = profileView;
        mParameters = new WeiboParameters(Constant.APP_KEY);
    }

    @Override
    public void loadUserInfo() {
        new BaseNetWork(mProfileView, Urls.USERSHOW) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mProfileView.getActivity()).getToken());
                mParameters.put(ParameterKey.UID,AccessTokenKeeper.readAccessToken(mProfileView.getActivity()).getUid());
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                UserEntity entity = new Gson().fromJson(response.response,UserEntity.class);
                mProfileView.onLoadUserInfo(entity);
            }
        }.get();
    }

    @Override
    public void logOut() {
        AccessTokenKeeper.clear(mProfileView.getActivity());
        mProfileView.getActivity().startActivity(new Intent(mProfileView.getActivity(), LanddingPageActivity.class));
        mProfileView.getActivity().finish();
    }
}
