package com.xiaoming.cweibo.views.MVP_view;

import com.xiaoming.cweibo.entities.UserEntity;

/**
 * @author slience
 * @des
 * @time 2017/5/2417:49
 */

public interface ProfileView extends BaseView{
    void onLoadUserInfo(UserEntity userEntity);
}
