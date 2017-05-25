package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.fragment.DiscoverFragment;
import com.xiaoming.cweibo.fragment.HomeFragment;
import com.xiaoming.cweibo.fragment.ProfileFragment;

import org.greenrobot.eventbus.EventBus;

public class HomePageActivity extends BaseActivity {

    private FrameLayout mFlContainer;
    private FragmentTabHost mTabHost;
    private RadioGroup mRgContainer;
    private RadioButton mRbHome;
    private RadioButton mRbMessage;
    private RadioButton mRbProfile;
    private Class fragment[];
    private int menu_id = R.menu.menu_home;
    private boolean isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setDisplayHomeAsUpEnabled(false).setTitle(R.string.app_name);
        fragment = new Class[]{HomeFragment.class, DiscoverFragment.class, ProfileFragment.class};
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    private void initView(){
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mRgContainer = (RadioGroup) findViewById(R.id.rg_container);
        mRbHome = (RadioButton) findViewById(R.id.rb_home);
        mRbMessage = (RadioButton) findViewById(R.id.rb_message);
        mRbProfile = (RadioButton) findViewById(R.id.rb_profile);
        mTabHost.setup(getApplicationContext(),getSupportFragmentManager(),R.id.fl_container);
        //添加标签
        for (int i = 0; i < fragment.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            mTabHost.addTab(tabSpec,fragment[i],null);
        }
        //设置默认为第一个对象
        mTabHost.setCurrentTab(0);
        mRgContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        mTabHost.setCurrentTab(0);
                        menu_id = R.menu.menu_home;
                        break;
                    case R.id.rb_message:
                        mTabHost.setCurrentTab(1);
                        menu_id = -1;
                        break;
                    case R.id.rb_profile:
                        mTabHost.setCurrentTab(2);
                        menu_id = -1;
                        break;
                }
                //重新调用menu
                supportInvalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu_id == -1){
            menu.clear();
        }else{
            getMenuInflater().inflate(menu_id,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_HOME:
                EventBus.getDefault().post("HOME");
                break;
            case R.id.action_ME:
                EventBus.getDefault().post("ME");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(isExit){
            this.finish();
        }else{
            Toast.makeText(this, R.string.toast_press_again_to_exit, Toast.LENGTH_SHORT).show();
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        }

    }
}
