package com.example.pratishparija.pos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pratishparija.pos.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginTypeActivity extends AppCompatActivity {
    @BindView(R.id.fab_catalog)
    FloatingActionButton fabCatalog;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.card_catlog_login)
    CardView cardCatlogLogin;
    @BindView(R.id.fab_cancel)
    FloatingActionButton fabCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_type);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.fab_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(LoginTypeActivity.this, CatalogDashboard.class);
                startActivity(intent);
                break;
            case R.id.fab_cancel:
                onBackPressed();
                break;
        }
    }
}
