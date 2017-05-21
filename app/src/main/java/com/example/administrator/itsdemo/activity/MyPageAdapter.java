package com.example.administrator.itsdemo.activity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class MyPageAdapter extends PagerAdapter
{
    private List<View> views;

    public MyPageAdapter(List<View> views)
    {
        this.views = views;
    }

    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(views.get(position));
    }
}
