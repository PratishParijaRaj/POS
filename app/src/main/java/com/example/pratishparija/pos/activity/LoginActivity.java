package com.example.pratishparija.pos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.services.FirebaseAuthenticationService;
import com.example.pratishparija.pos.models.TenantProfile;
import com.example.pratishparija.pos.utils.Constants;
import com.example.pratishparija.pos.utils.MyPreferense;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements FirebaseAuthenticationService.FirebaseAuthenticationCallback {
    FirebaseAuthenticationService mFire;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        myClick();
    }

    private void myClick() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        SpannableString spannableString = new SpannableString("Don't have an account? REGISTER");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegister.setText(spannableString);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    protected void logIn(String email, String password) {
        if (email.isEmpty()) {
            invalidEmail();
            return;
        }
        if (password.isEmpty()) {
            invalidPassword();
            return;
        }
        if (isNetworkAvailable()) {
            progressDialog.show();
            TenantProfile tenantProfile = new TenantProfile();
            tenantProfile.setEmail(email);
            tenantProfile.setPassword(password);
            mFire = new FirebaseAuthenticationService(this);
            mFire.authenticateExistingTenantWithEmailAndPassword(tenantProfile);
        } else {
            Toast.makeText(this, "No Internate", Toast.LENGTH_SHORT).show();

        }
    }


    @OnClick({R.id.btn_login, R.id.tv_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String emailId = etEmail.getText().toString().trim();
                String userPassword = etPassword.getText().toString().trim();
                logIn(emailId, userPassword);

                break;
            case R.id.tv_forgot_password:
//                firebaseAuth.signOut();
                break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void invalidEmail() {
        btnLogin.setEnabled(true);
        etEmail.setError("Emailid can't be null");
    }

    public void invalidPassword() {
        btnLogin.setEnabled(true);
        etPassword.setError("Password can't be null");
    }

    @Override
    public void onSuccess(String firebaseUser) {
        progressDialog.dismiss();
        MyPreferense.setLoginStatus(Constants.LOGIN_SUCCESS);
        MyPreferense.setUserId(firebaseUser);
        Intent intent = new Intent(LoginActivity.this, ApplicationLoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "f", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Exception e) {
        progressDialog.dismiss();
        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
    }
}
