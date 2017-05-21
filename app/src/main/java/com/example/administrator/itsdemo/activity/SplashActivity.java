package com.example.administrator.itsdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.administrator.itsdemo.R;

public class SplashActivity extends AppCompatActivity
{
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        linearLayout = (LinearLayout) findViewById(R.id.splash_ll);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        linearLayout.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
