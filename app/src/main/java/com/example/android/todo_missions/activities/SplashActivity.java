package com.example.android.todo_missions.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.todo_missions.R;


public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LinearLayout threeLinesLinearLayout = findViewById(R.id.activity_splash_three_lines);
        TextView appNameTextView = findViewById(R.id.activity_splash_app_description_name);

        Animation animationBottom = AnimationUtils.loadAnimation(this, R.anim.animation_bottom);
        threeLinesLinearLayout.setAnimation(animationBottom);
//        appNameTextView.setAnimation(animationBottom);

        int SPLASH_SCREEN = 2500;
        new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);

        }
    }