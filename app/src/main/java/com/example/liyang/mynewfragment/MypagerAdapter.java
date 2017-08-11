package com.example.liyang.mynewfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.ArrayList;
/**
 * Created by liyang on 2017/4/5.
 */

public class MypagerAdapter extends FragmentStatePagerAdapter{
    private ArrayList<Fragment> fragmentsList;

    public MypagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MypagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
