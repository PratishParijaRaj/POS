package com.example.pratishparija.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.services.FirebaseAuthenticationService;
import com.example.pratishparija.pos.utils.MyPreferense;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplicationLoginActivity extends AppCompatActivity implements FirebaseAuthenticationService.FirebaseAuthenticationCallback {
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.tv_myapp)
    TextView tvMyapp;
    @BindView(R.id.btn_logout)
    ImageView btnLogout;
    @BindView(R.id.fab_catalog)
    FloatingActionButton fabCatalog;
    @BindView(R.id.fab_checkout)
    FloatingActionButton fabCheckout;
    @BindView(R.id.fab_store)
    FloatingActionButton fabStore;
    @BindView(R.id.store)
    TextView store;
    @BindView(R.id.checkout)
    TextView checkout;
    @BindView(R.id.catalog)
    TextView catalog;
    @BindView(R.id.btn_launch)
    AppCompatButton btnLaunch;
    @BindView(R.id.card_login)
    CardView cardLogin;
    FirebaseAuthenticationService mFire;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_login);
        ButterKnife.bind(this);
        MyPreferense.init(this);
        mFire = new FirebaseAuthenticationService(this);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @OnClick({R.id.btn_logout, R.id.fab_catalog, R.id.fab_checkout, R.id.fab_store, R.id.btn_launch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                MyPreferense.clearAll();
                if (isNetworkAvailable()) {
                    mFire.signOut();
                    Intent intent = new Intent(ApplicationLoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No Internate", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fab_catalog:
                break;
            case R.id.fab_checkout:
                break;
            case R.id.fab_store:
                Intent intent2 = new Intent(ApplicationLoginActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_launch:
                Intent intent1 = new Intent(ApplicationLoginActivity.this, LoginTypeActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onSuccess(String firebaseUser) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onError(Exception e) {

    }
}
