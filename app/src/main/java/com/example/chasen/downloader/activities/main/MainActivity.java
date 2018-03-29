package com.example.chasen.downloader.activities.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chasen.downloader.R;
import com.example.chasen.downloader.frags.downloaded.DownloadedFragment;
import com.example.chasen.downloader.frags.downloading.DownloadingFragment;
import com.example.chasen.downloader.widget.ChangeColorTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, MainActivityContract.MainView{

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_downloading)
    ChangeColorTab mDownloadingTab;
    @BindView(R.id.tab_downloaded)
    ChangeColorTab mDownloadedTab;


    private List<Fragment> mFragList;
    private FragmentPagerAdapter mAdapter;
    private List<ChangeColorTab> mTabs;
    private MainActivityPresent mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initWidget();
        mPresent.checkPermission(this);
    }

    private void initData() {
        mFragList = new ArrayList<>();
        mFragList.add(new DownloadingFragment());
        mFragList.add(new DownloadedFragment());
        mTabs = new ArrayList<>();
        mTabs.add(mDownloadingTab);
        mTabs.add(mDownloadedTab);
        mPresent = new MainActivityPresent(this);
    }

    private void initWidget() {
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragList.get(position);
            }

            @Override
            public int getCount() {
                return mFragList.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        // 第一次打开，默认选择第一个tab
        mDownloadingTab.setIconAlpha(1.0f);
    }

    // 当 正在下载 tab被点击
    @OnClick(R.id.tab_downloading)
    void onDownloadingTabClick() {
        resetTab();
        mTabs.get(0).setIconAlpha(1.0f);
        mViewPager.setCurrentItem(0, false);
    }

    // 当 已下载 tab被点击
    @OnClick(R.id.tab_downloaded)
    void onDownloadedTabClick() {
        resetTab();
        mTabs.get(1).setIconAlpha(1.0f);
        mViewPager.setCurrentItem(1, false);
    }

    // 重置其他tab的颜色
    private void resetTab() {
        for (ChangeColorTab mTab : mTabs) {
            mTab.setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorTab left = mTabs.get(position);
            ChangeColorTab right = mTabs.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresent.checkPermissionResult(this, requestCode);
    }

    @Override
    public void showCheckPermissionResult() {
        Toast.makeText(this, "请为软件授予权限", Toast.LENGTH_SHORT).show();
        finish();
    }


}


