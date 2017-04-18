package com.colin.livetvtab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.colin.livetvtab.customview.ColorTrackView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private List<Fragment> fragments;
    private SelectFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private ColorTrackView ctv_1, ctv_3, ctv_2;
    private List<ColorTrackView> colorTrackViews;
    private View flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

        initViewPager();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_activity_main);
        ctv_1 = (ColorTrackView) findViewById(R.id.ctv_1);
        ctv_2 = (ColorTrackView) findViewById(R.id.ctv_2);
        ctv_3 = (ColorTrackView) findViewById(R.id.ctv_3);
        flag = findViewById(R.id.flag);
        colorTrackViews = new ArrayList<>();
        colorTrackViews.add(ctv_1);
        colorTrackViews.add(ctv_2);
        colorTrackViews.add(ctv_3);
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MyFragment myFragment = MyFragment.getMyFragment(i);
            fragments.add(myFragment);
        }
        pagerAdapter = new SelectFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new SelectOnPageChangeListener());
    }

    private class SelectOnClickListener implements View.OnClickListener {
        private int index = 0;

        public SelectOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);
            changeColorTrackViewColor(index);
            setLeftMargin(index);
        }

    }

    private void setListener() {

        ctv_1.setOnClickListener(new SelectOnClickListener(0));
        ctv_2.setOnClickListener(new SelectOnClickListener(1));
        ctv_3.setOnClickListener(new SelectOnClickListener(2));
    }

    private void changeColorTrackViewColor(int index) {
        ctv_1.setDirection(0);
        ctv_1.setProgress(((index == 0) ? 1.0f : 0));
        ctv_2.setDirection(0);
        ctv_2.setProgress(((index == 1) ? 1.0f : 0));
        ctv_3.setDirection(0);
        ctv_3.setProgress(((index == 2) ? 1.0f : 0));
    }

    public class SelectOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private boolean checkIfScroll = false;

        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 1) {
                checkIfScroll = true;
            } else if (arg0 == 0) {
                checkIfScroll = false;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            if (checkIfScroll) {
                if (positionOffset > 0) {
                    ColorTrackView left = colorTrackViews.get(position);
                    ColorTrackView right = colorTrackViews.get(position + 1);

                    left.setDirection(1);
                    right.setDirection(0);
                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                    float value = positionOffset + position;
                    setLeftMargin(value);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {

        }
    }

    private void setLeftMargin(float value) {
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) flag.getLayoutParams();
        rl.leftMargin = (int) (ScreenUtil.dip2px(60) * value);
        flag.setLayoutParams(rl);
    }
}
