package com.xiaoming.cweibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.activity.FansActivity;
import com.xiaoming.cweibo.entities.UserEntity;
import com.xiaoming.cweibo.presenter.ProfilePresenter;
import com.xiaoming.cweibo.presenter.ProfilePresenterImp;
import com.xiaoming.cweibo.utils.CircleTransform;
import com.xiaoming.cweibo.views.MVP_view.ProfileView;

/**
 * @author Slience_Manager
 * @time 2017/5/17 13:40
 */

public class ProfileFragment extends BaseFragment implements ProfileView,View.OnClickListener{

    private ImageView mIvHeader;
    private TextView mTvUserName;
    private TextView mTvDes;
    private TextView mTvAttentions;
    private TextView mTvFans;
    private TextView mTvLoginOut;
    private ProfilePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenterImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        init(view);
        mPresenter.loadUserInfo();
        return view;
    }

    private void init(View view) {
        mIvHeader = (ImageView) view.findViewById(R.id.ivHeader);
        mTvUserName = (TextView) view.findViewById(R.id.tvUserName);
        mTvDes = (TextView) view.findViewById(R.id.tvDes);
        mTvAttentions = (TextView) view.findViewById(R.id.tvAttentions);
        mTvFans = (TextView) view.findViewById(R.id.tvFans);
        mTvLoginOut = (TextView) view.findViewById(R.id.tvLoginOut);

        mTvAttentions.setOnClickListener(this);
        mTvFans.setOnClickListener(this);
        mTvLoginOut.setOnClickListener(this);
    }

    @Override
    public void onLoadUserInfo(UserEntity userEntity) {
        Glide.with(getActivity()).load(userEntity.profile_image_url).transform(new CircleTransform(getActivity())).into(mIvHeader);
        mTvUserName.setText(userEntity.screen_name);
        mTvDes.setText(userEntity.description);
    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()){
            case R.id.tvAttentions:
                intent = new Intent(getActivity(),FansActivity.class);
                intent.setAction("tvAttentions");
                startActivity(intent);
                break;
            case R.id.tvFans:
                intent = new Intent(getActivity(),FansActivity.class);
                intent.setAction("tvFans");
                startActivity(intent);
                break;
            case R.id.tvLoginOut:
                mPresenter.logOut();
                break;
        }

    }
}
