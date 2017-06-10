# Android ViewPager使用
ViewPager是Android的一个UI组件，用于视图切换，我们可以往里面填充多个View，需要用到适配器。<br>

- PagerAdapter，数据源：`List<View>`
- FragmentPagerAdapter，数据源：`List<Fragment>`
- FragmentStatePagerAdapter，数据源：`List<Fragment>`

分析适配器：<br>

- PagerAdapter是ViewPager提供的一个适配器，是一个抽象类，直接继承于Object，导入包android.support.v4.view.PagerAdapter即可使用。要使用PagerAdapter，要重写四个方法：<br>
获取当前窗体界面数：<br>
`public abstract int getCount();`
确定一个页面视图是否关联到一个特定的对象，即用来判断pager的一个view是否和instantiateItem方法返回的object有关联：<br>
`public abstract boolean isViewFromObject(android.view.View arg0, java.lang.Object arg1);`
返回给定对象的位置，表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中：<br>
`public java.lang.Object instantiateItem(android.view.View container, int position);`
从ViewGroup中移出当前View：<br>
`public void destroyItem(android.view.ViewGroup container, int position, java.lang.Object object);`
- FragmentPagerAdapter，继承自PagerAdapter，这个适配器就是用来实现Fragment在ViewPager里面进行滑动切换，缓存当前的Fragment和左边一个Fragment，右边一个Fragment，一共缓存3个Fragment。适合使用在Fragment固定的数量较少的场景。要使用FragmentPagerAdapter，我们需要实现2个方法。<br>
返回的是ViewPager页面的数量：<br>
`getCount()`
返回的是要显示的fragment对象：<br>
`getItem(int position)`
- FragmentStatePagerAdapter，是PagerAdapter的子类，当Fragment对用户不可见时，整个Fragment会被销毁，只会保存Fragment的状态，需要重新显示的时候，生成新的页面，适合于页面较多或者页面内容非常复杂的场景。使用FragmentStatePagerAdapter，需要实现2个方法和FragmentPagerAdapter一样。<br>

---
PagerAdapter实例：<br>
效果预设：三个页面，通过手势滑动完成切换。<br>
目录结构：<br>
![directory1](https://raw.githubusercontent.com/douerza/picture/master/ViewPager/PagerAdapter/directory1.png)<br>

activity_main.xml中写一个ViewPager：<br>
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.douzaer.viewpagerdemo.MainActivity">
    <android.support.v4.view.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
    </android.support.v4.view.ViewPager>
</android.support.constraint.ConstraintLayout>
```
view1，view2，view3分别写一个TextView（只贴出view1布局代码，view2，view3同理）：<br>
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.douzaer.viewpagerdemo.MainActivity">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="第一个页面"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
```
MyPagerAdapter.java中自定义PagerAdapter：<br>
```
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

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
```
MainActivity.java，主要由三部分构成，构造适配器，数据源，加载适配器：
```
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
```
效果图：<br>
![GIF1](https://raw.githubusercontent.com/douerza/picture/master/ViewPager/PagerAdapter/GIF1.gif)<br>
Demo代码：[PagerAdapterDemo](https://github.com/douerza/ViewPagerDemo)<br>

---
FragmentPagerAdapter实例：<br>
效果预设：三个页面，通过手势滑动完成切换。<br>
目录结构：<br>
![directory2](https://raw.githubusercontent.com/douerza/picture/master/ViewPager/FragmentPagerAdapter/directory2.png)<br>
activity_main.xml中写一个ViewPager：<br>
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.douzaer.fragmentpageradapterdemo.MainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager1"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
    </android.support.v4.view.ViewPager>

</android.support.constraint.ConstraintLayout>
```
简单写三个fragment布局（其余两个同理）：<br>
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:id="@+id/tv1"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:text="第1个fragment"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        >
    </TextView>
</android.support.constraint.ConstraintLayout>
```
写三个自定义的Fragment1、Fragment2、Fragment3继承Fragment类（其余两个同理）：<br>
```
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {
    public Fragment1() {
    }
    //对应布局fg1
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg1, container, false);
    }
}
```
自定义MyFragmentPagerAdapter继承FragmentPagerAdapter：<br>
```
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //总共3个fragment
    private final int PAGER_COUNT = 3;
    //声明3个Fragment对象
    private Fragment1 myFragment1 = null;
    private Fragment2 myFragment2 = null;
    private Fragment3 myFragment3 = null;
    
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new Fragment1();
        myFragment2 = new Fragment2();
        myFragment3 = new Fragment3();
    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }
    //根据不同的页面常量加载不同的fragment
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
        }
        return fragment;
    }
}
```
MainActivity.java：<br>
```
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    //页面常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    //自定义的适配器对象
    private MyFragmentPagerAdapter mAdapter;
    //定义ViewPager对象
    private ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用自定义的MyFragmentPagerAdapter实例化mAdapter对象
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager1);
        //加载适配器
        mViewPager.setAdapter(mAdapter);
    }
}

```
效果图：<br>
![GIF2](https://raw.githubusercontent.com/douerza/picture/master/ViewPager/FragmentPagerAdapter/GIF2.gif)<br>
Demo代码：[FragmentPagerAdapterDemo](https://github.com/douerza/FragmentPagerAdapterDemo)<br>

