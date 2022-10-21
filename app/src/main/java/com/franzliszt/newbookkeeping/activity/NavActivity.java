package com.franzliszt.newbookkeeping.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.franzliszt.newbookkeeping.R;
import com.franzliszt.newbookkeeping.ui.dashboard.GeneralFragment;
import com.franzliszt.newbookkeeping.ui.record.RecordFragment;
import com.franzliszt.newbookkeeping.adapter.BottomAdapter;
import com.franzliszt.newbookkeeping.utils.KillProcess;
import com.franzliszt.newbookkeeping.utils.StatusBarUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class NavActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarHide(getWindow());
        setContentView(R.layout.activity_nav);
        InitView();
        NavListener();
    }
    private void InitView(){
        navView = findViewById(R.id.navView);
        mViewPager = findViewById(R.id.mViewPager);
        KillProcess.PUSH(NavActivity.class,this);
    }
    private void NavListener(){
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_navigation_record:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.menu_navigation_general:
                        mViewPager.setCurrentItem(1);
                        return true;
                }
                return false;
            }
        });
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecordFragment());
        adapter.addFragment(new GeneralFragment());
        viewPager.setAdapter(adapter);
    }

}