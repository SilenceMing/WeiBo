package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.entities.PicUrlsEntity;

import uk.co.senab.photoview.PhotoView;

/**
 * @author slience
 * @des
 * @time 2017/5/220:29
 */

public class PhotoViewActivity extends BaseActivity {
    private PhotoView mPhotoView;
    private PicUrlsEntity mPicUrlsEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        mPicUrlsEntity = (PicUrlsEntity) getIntent().getSerializableExtra("IMG_URL");
        init();
    }

    private void init() {
        mPhotoView = (PhotoView) findViewById(R.id.photoView);
        Glide.with(getApplicationContext()).load(mPicUrlsEntity.bmiddle_pic).placeholder(R.mipmap.ic_place_holder).into(mPhotoView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photoview;
    }
}
