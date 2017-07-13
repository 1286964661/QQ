package com.example.adminstrator.qq.loginsuccess;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.adminstrator.qq.R;
import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adminstrator on 2017/7/1.
 */

public class LoginSuccess extends Activity implements View.OnClickListener {
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    //保存四个界面的view
    private List<View> mViews = new ArrayList<>();
    //底部的四个tab按钮
    private LinearLayout mTabMessage;
    private LinearLayout mTabFrd;
    private LinearLayout mTabSpace;
    private LinearLayout mTabSetting;

    private ImageButton mMessageImg;
    private ImageButton mFrdImg;
    private ImageButton mSpaceImg;
    private ImageButton mSettingImg;

    private Button btAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        initView();
        initEvents();
        PushManager.getInstance().initialize(this.getApplicationContext(), com.example.adminstrator.qq.test.DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.example.adminstrator.qq.test.DemoIntentService.class);
    }

    private void initEvents() {
        mTabMessage.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabSpace.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当viewpager滑动时，对应的底部导航按钮的图片要变化
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem) {
                    case 0:
                        mMessageImg.setImageResource(R.drawable.tab_message_pressed);
                        break;
                    case 1:
                        mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
                        break;
                    case 2:
                        mSpaceImg.setImageResource(R.drawable.tab_space_pressed);
                        break;
                    case 3:
                        btAbout = (Button) findViewById(R.id.about);
                        btAbout.setOnClickListener(LoginSuccess.this);
                        mSettingImg.setImageResource(R.drawable.tab_settings_pressed);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetImg() {
        mMessageImg.setImageResource(R.drawable.tab_message_normal);
        mFrdImg.setImageResource(R.drawable.tab_find_frd_normal);
        mSpaceImg.setImageResource(R.drawable.tab_space_normal);
        mSettingImg.setImageResource(R.drawable.tab_settings_normal);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabMessage = (LinearLayout) findViewById(R.id.id_tab_message);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabSpace = (LinearLayout) findViewById(R.id.id_tab_space);
        mTabSetting = (LinearLayout) findViewById(R.id.id_tab_setting);
        mFrdImg = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mSpaceImg = (ImageButton) findViewById(R.id.id_tab_space_img);
        mMessageImg = (ImageButton) findViewById(R.id.id_tab_message_img);
        mSettingImg = (ImageButton) findViewById(R.id.id_tab_setting_img);
        //通过LayoutInflater引入布局，并将布局转换为view
        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.tab01, null);
        View tab02 = mInflater.inflate(R.layout.tab02, null);
        View tab03 = mInflater.inflate(R.layout.tab03, null);
        View tab04 = mInflater.inflate(R.layout.tab04, null);
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        //初始化PagerAdapter
        mAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return  view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
               return view == object;
            }
        };
        mViewPager.setAdapter(mAdapter);
    }









    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.id_tab_message:
                mViewPager.setCurrentItem(0);
                mMessageImg.setImageResource(R.drawable.tab_message_pressed);
                break;
            case R.id.id_tab_frd:
                mViewPager.setCurrentItem(1);
                mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case R.id.id_tab_space:
                mViewPager.setCurrentItem(2);
                mSpaceImg.setImageResource(R.drawable.tab_space_pressed);
                break;
            case R.id.id_tab_setting:
                mViewPager.setCurrentItem(3);
                btAbout = (Button) findViewById(R.id.about);
                btAbout.setOnClickListener(this);
                mSettingImg.setImageResource(R.drawable.tab_settings_pressed);
                break;

            case R.id.about:
                mSettingImg.setImageResource(R.drawable.tab_settings_pressed);
                new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.app_name).setView(R.layout.about).create().show();
                break;
            default:
                break;
        }
    }
}
