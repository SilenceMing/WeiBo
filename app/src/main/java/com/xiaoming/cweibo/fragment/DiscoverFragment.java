package com.xiaoming.cweibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.activity.CommentActivity;
import com.xiaoming.cweibo.activity.CollectActivity;
import com.xiaoming.cweibo.activity.MentionActivity;

/**
 * @author Slience_Manager
 * @time 2017/5/17 13:40
 */

public class DiscoverFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlAt;
    private LinearLayout mLlComments;
    private LinearLayout mLlLike;
    private TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_discover,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        mLlAt = (LinearLayout) view.findViewById(R.id.llAt);
        mLlComments = (LinearLayout) view.findViewById(R.id.llComments);
        mLlLike = (LinearLayout) view.findViewById(R.id.llLike);
        mLlAt.setOnClickListener(this);
        mLlComments.setOnClickListener(this);
        mLlLike.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class mClass = null;
        switch (v.getId()){
            case R.id.llAt:
                mClass = MentionActivity.class;
                break;
            case R.id.llComments:
                mClass = CommentActivity.class;
                break;
            case R.id.llLike:
                mClass = CollectActivity.class;
                break;
        }
        Intent intent = new Intent(getActivity(),mClass);
        startActivity(intent);
    }
}
