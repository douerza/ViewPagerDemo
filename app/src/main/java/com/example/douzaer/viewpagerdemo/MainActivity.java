package com.example.douzaer.viewpagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    //数据源
    private ArrayList<View> al;
    //适配器
    private MyPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp);
        //加载数据
        al = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        al.add(li.inflate(R.layout.view1,null,false));
        al.add(li.inflate(R.layout.view2,null,false));
        al.add(li.inflate(R.layout.view3,null,false));
        //构造适配器
        mAdapter = new MyPagerAdapter(al);
        //加载适配器
        vp.setAdapter(mAdapter);
    }
}