package com.example.pratishparija.pos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratishparija.pos.models.UserRole;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        login.setEnabled(false);

    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
//        registerUser();
    }

    private void registerUser() {
        final String emailId = email.getText().toString().trim();
        final String userPassword = password.getText().toString().trim();
        String userConfirmPassword = connfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(emailId)) {
            email.setError("Enter EmaiId");
            login.setEnabled(false);
        }
        if (TextUtils.isEmpty(userPassword) || userPassword.length() <= 7) {
            password.setError("Atlest 8 characters");
            login.setEnabled(false);

        }

        if (TextUtils.isEmpty(userConfirmPassword) || connfirmPassword.length() <= 7) {
            connfirmPassword.setError("Atlest 8 characters");
            login.setEnabled(false);

        }
        progressDialog.setMessage("Registering User");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emailId, userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserRole role = new UserRole();
                            updateUser(role);
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Could not register..Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUser(UserRole role) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("user").setValue(role).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterActivity.this, task.isSuccessful() ? "Success" : "Failed", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
