package com.example.pratishparija.pos.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pratishparija.pos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.connfirm_password)
    EditText connfirmPassword;
    @BindView(R.id.login)
    AppCompatButton login;
    @BindView(R.id.terms)
    TextView terms;
    @BindView(R.id.already)
    TextView already;
    @BindView(R.id.cb_password)
    CheckBox cbPassword;
    @BindView(R.id.cb_confirm_password)
    CheckBox cbConfirmPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        myClick();
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isChecked()) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }

        });
        cbConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isChecked()) {
                    connfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    connfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }

        });
    }

    private void myClick() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        SpannableString ss = new SpannableString("Already have an account? LOG IN");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 24, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView textView = (TextView) findViewById(R.id.already);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        String emailId = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userConfirmPassword = connfirmPassword.getText().toString().trim();
        userLogin(emailId, userPassword, userConfirmPassword);
    }

    private void userLogin(String email, String password, String cPassword) {
        if (email.isEmpty()) {
            invalidEmail();
            return;
        }
        if (password.length() < 8) {
            invalidPassword();
            return;
        }
        if (cPassword.isEmpty() || !(password.equals(cPassword))) {
            invalidConfirmPassword();
            return;
        }
        Intent intent1 = new Intent(RegisterActivity.this, SetupProfile.class);
        intent1.putExtra("email", email);
        intent1.putExtra("password", cPassword);
        startActivity(intent1);
    }

    public void invalidEmail() {
        login.setEnabled(true);
        email.setError("Emailid can't be null");
    }

    public void invalidPassword() {
        login.setEnabled(true);
        password.setError("Not valid");
    }


    public void invalidConfirmPassword() {
        login.setEnabled(true);
        connfirmPassword.setError("Password does not match");

    }

    public static boolean isValidPassword(final String myPassword) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(myPassword);
        return matcher.matches();

    }

}
