package com.xiaoming.cweibo.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.views.MVP_view.BaseView;

/**
 * @author Slience_Manager
 * @time 2017/5/17 1:15
 */

public class BaseFragment extends Fragment implements BaseView{
    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}
