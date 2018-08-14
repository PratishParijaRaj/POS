package com.example.pratishparija.pos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.utils.Constants;
import com.example.pratishparija.pos.utils.MyPreferense;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyPreferense.init(this);
        String status = MyPreferense.getLoginStatus();
        if (status.equalsIgnoreCase(Constants.LOGIN_SUCCESS)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, ApplicationLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
    }
}