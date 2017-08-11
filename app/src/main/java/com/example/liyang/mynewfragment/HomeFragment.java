package com.example.liyang.mynewfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by liyang on 2017/4/5.
 */

public class HomeFragment extends Fragment {

    Resources resources;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tvTabNew, tvTabHot,tvTabsour;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private  int position_two;
    public final static int num = 3;
    private View view;
    Fragment home1;
    Fragment home2;
    Fragment home3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
            if(savedInstanceState==null) {
                System.out.println("第一次创建homefragment");
                view = inflater.inflate(R.layout.homefragment, null);
                resources = getResources();
                InitWidth(view);
                InitTextView(view);

                InitViewPager(view);
                //displayGoods(view);
                TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
                tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                animation.setFillAfter(true);
                animation.setDuration(300);
                ivBottomLine.startAnimation(animation);


                return view;
            }
            else {
                System.out.println("这是恢复之前的");
                return view;
            }
    }
    //实现Viewlist在view界面展示商品信息

    private void InitTextView(View parentView) {
        tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
        tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);
        tvTabsour=(TextView) parentView.findViewById(R.id.tv_tab_3);

        tvTabNew.setOnClickListener(new MyOnClickListener(0));
        tvTabHot.setOnClickListener(new MyOnClickListener(1));
        tvTabsour.setOnClickListener(new MyOnClickListener(2));
    }

    private void InitViewPager(View parentView) {
        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
        mPager.setOffscreenPageLimit(3);
        fragmentsList = new ArrayList<Fragment>();

        home1 = new homefragment_1();
        home2 = new homefragment_2();
        home3 = new homefragment_3();
        fragmentsList.add(home1);
        fragmentsList.add(home2);
        fragmentsList.add(home3);
        MypagerAdapter adapter=new MypagerAdapter(getChildFragmentManager(),fragmentsList);
        mPager.setAdapter(adapter);

        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        //mPager.setOffscreenPageLimit(3);
        mPager.setCurrentItem(0);

    }

    private void InitWidth(View parentView) {
        ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        int avg = (int) (screenW / num);
        position_one = avg + offset;
        position_two= 2*avg+offset;


    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    public class MyOnPageChangeListener implements OnPageChangeListener {
            /*实现滑动动画，并且监听是从哪个模块跳转的，
            *currIndex记录的是之前的页面数，case记录的是当前页面
            * 判断没中跳转的情况，然后进行颜色变化和动画跳转。
             */
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if(currIndex==1)
                    {
                        animation = new TranslateAnimation(position_one, offset, 0, 0);
                        tvTabNew.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    if(currIndex==0)
                    {
                        animation = new TranslateAnimation(offset, offset, 0, 0);
                        tvTabNew.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    if(currIndex==2)
                    {
                        animation = new TranslateAnimation(position_two, offset, 0, 0);
                        tvTabNew.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    break;
                case 1:

                    if(currIndex==2)
                    {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        tvTabHot.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    if(currIndex==0)
                    {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTabHot.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    if(currIndex==1)
                    {
                        animation = new TranslateAnimation(position_one, position_one, 0, 0);
                        tvTabHot.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabsour.setTextColor(resources.getColor(R.color.guide_hui));
                    }

                    break;
                case 2:

                    if(currIndex==1)
                    {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        tvTabsour.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                    }

                    if(currIndex==0)
                    {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        tvTabsour.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    if(currIndex==2)
                    {
                        animation = new TranslateAnimation(position_two, position_two, 0, 0);
                        tvTabsour.setTextColor(resources.getColor(R.color.avoscloud_blue));
                        tvTabHot.setTextColor(resources.getColor(R.color.guide_hui));
                        tvTabNew.setTextColor(resources.getColor(R.color.guide_hui));
                    }
                    break;


            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
