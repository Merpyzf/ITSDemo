package com.example.administrator.itsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.itsdemo.MainActivity;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.PreUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener
{
    private RelativeLayout layout;
    private ViewPager viewPager;
    private Button start;

    private List<View> views = new ArrayList<>();
    private List<ImageView> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        initData();
    }

    private void initView()
    {
        layout = (RelativeLayout) findViewById(R.id.guide_rLayout);
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        start = (Button) findViewById(R.id.guide_btn_start);

        LinearLayout linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 200;
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < 3; i++)
        {
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.shape_grey_point);
            linearLayout.addView(image);
            images.add(image);
        }
        layout.addView(linearLayout);
        images.get(0).setImageResource(R.drawable.shape_red_point);

        viewPager.addOnPageChangeListener(this);

        start.setOnClickListener(this);
    }

    private void initData()
    {
       if(PreUtils.getBoolean(this, "guide", false))
       {
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
       }

        View view1 = View.inflate(this, R.layout.view, null);
        View view2 = View.inflate(this, R.layout.view, null);
        View view3 = View.inflate(this, R.layout.view, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        MyPageAdapter pageAdapter = new MyPageAdapter(views);
        viewPager.setAdapter(pageAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        Log.i("main", position+";;;");
        for (int i = 0; i < images.size(); i++)
        {
            if(position == i)
            {
                images.get(i).setImageResource(R.drawable.shape_red_point);
            }
            else
            {
                images.get(i).setImageResource(R.drawable.shape_grey_point);
            }
        }

        if(position == 2)
        {
            start.setVisibility(View.VISIBLE);
        }
        else
        {
            start.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        PreUtils.setBoolean(this, "guide", true);
        finish();
    }
}
