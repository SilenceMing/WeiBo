package com.xiaoming.cweibo.views.MVP_view;

import android.app.Activity;

/**
 * @author slience
 * @des
 * @time 2017/5/2312:23
 */

public interface BaseView {
    Activity getActivity();
    void onError(String error);
    void showLoadingView();
    void hideLoadingView();
}
