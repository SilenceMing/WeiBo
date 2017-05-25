package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaoming.cweibo.R;

/**
 * @author slience
 * @des
 * @time 2017/5/24 18:27
 */

public class AttentionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_my_attention);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_attention;
    }
}
