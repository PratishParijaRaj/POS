package com.example.pratishparija.pos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.utils.MyPreferense;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.fooo)
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyPreferense.init(this);
        tvForgotPassword.setText(MyPreferense.getUserId());
    }
}
