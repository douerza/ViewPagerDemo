package com.example.douzaer.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    public MyPagerAdapter() {
    }

    public MyPagerAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    //获得viewpager中view数目
    @Override
    public int getCount() {
        return viewLists.size();
    }

    //判断instantiateItem函数所返回来与一个页面视图是否是代表的同一个视图
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //将给定位置的view添加到ViewGrou中，创建并显示出来，返回一个代表新增页面的Object，通常都是直接返回view本身
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    //移除一个给定位置的页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}
