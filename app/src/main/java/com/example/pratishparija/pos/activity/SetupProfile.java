package com.example.pratishparija.pos.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.services.FirebaseAuthenticationService;
import com.example.pratishparija.pos.models.TenantProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SetupProfile extends AppCompatActivity implements FirebaseAuthenticationService.FirebaseAuthenticationCallback {
    @BindView(R.id.textView)
    TextView tv_msg;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.btn_setup_profile)
    Button btn_setup_profile;
    @BindView(R.id.tv4)
    TextView tvFirstName;
    @BindView(R.id.et4)
    EditText etFirstName;
    @BindView(R.id.tv5)
    TextView tvLastName;
    @BindView(R.id.et5)
    EditText etLastName;
    @BindView(R.id.tv6)
    TextView tvPhone;
    @BindView(R.id.et6)
    EditText etPhone;
    @BindView(R.id.next1)
    TextView next1;
    @BindView(R.id.tv1)
    TextView tvCountry;
    @BindView(R.id.et1)
    EditText etCountry;
    @BindView(R.id.tv2)
    TextView tvState;
    @BindView(R.id.et2)
    EditText etState;
    @BindView(R.id.tv3)
    TextView tvCity;
    @BindView(R.id.et3)
    EditText etCity;
    @BindView(R.id.next2)
    TextView next2;
    @BindView(R.id.back1)
    TextView back1;
    @BindView(R.id.tv7)
    TextView tvAddressLine1;
    @BindView(R.id.et7)
    EditText etAddressLine1;
    @BindView(R.id.tv8)
    TextView tvAddressLine2;
    @BindView(R.id.et8)
    EditText etAddressLine2;
    @BindView(R.id.tv9)
    TextView tvZipcode;
    @BindView(R.id.et9)
    EditText etZipcode;
    @BindView(R.id.next3)
    TextView next3;
    @BindView(R.id.back2)
    TextView back2;
    @BindView(R.id.profile_image_2)
    CircleImageView profileImage2;
    @BindView(R.id.notify)
    TextView notify;
    @BindView(R.id.terms)
    TextView terms;
    @BindView(R.id.finish)
    TextView finish;
    @BindView(R.id.back3)
    TextView back3;
    @BindView(R.id.cb_notify)
    CheckBox cbNotify;
    @BindView(R.id.cb_terms)
    CheckBox cbTerms;
    @BindView(R.id.card_setup_profile)
    CardView cardSetupProfile;
    @BindView(R.id.card_name_detail)
    CardView cardNameDetail;
    @BindView(R.id.card_country_detail)
    CardView cardCountryDetail;
    @BindView(R.id.card_address_detail)
    CardView cardAddressDetail;
    @BindView(R.id.card_terms)
    CardView cardTerms;

    private String firstName = "";
    private String lastName = "";
    private String phoneNo = "";
    private String country = "";
    private String state = "";
    private String city = "";
    private String emailId = "";
    private String password = "";

    private String addressLine1 = "";
    private String addressLine2 = "";
    private String zipcode = "";
    TenantProfile tenantProfile = new TenantProfile();
    private FirebaseAuthenticationService mfire;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_profile);
        ButterKnife.bind(this);
        mfire = new FirebaseAuthenticationService(this);
        emailId = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        progressDialog=new ProgressDialog(this);
    }

    @OnClick({R.id.next1, R.id.next2, R.id.back1, R.id.next3, R.id.back2, R.id.finish, R.id.back3, R.id.btn_setup_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next1:
                firstName = etFirstName.getText().toString().trim();
                lastName = etLastName.getText().toString().trim();
                phoneNo = etPhone.getText().toString().trim();
                validateName(firstName, lastName, phoneNo);

                break;
            case R.id.next2:
                country = etCountry.getText().toString().trim();
                state = etState.getText().toString().trim();
                city = etCity.getText().toString().trim();
                tenantProfile.setCountry(country);
                tenantProfile.setCity(city);
                tenantProfile.setState(state);
                cardAddressDetail.setVisibility(View.VISIBLE);
                cardCountryDetail.setVisibility(View.GONE);
                tv_msg.setText("Where you live?");
                break;
            case R.id.back1:
                cardCountryDetail.setVisibility(View.GONE);
                cardNameDetail.setVisibility(View.VISIBLE);
                tv_msg.setText("Help us with your profile information.");
                break;
            case R.id.next3:
                addressLine1 = etAddressLine1.getText().toString().trim();
                addressLine2 = etAddressLine2.getText().toString().trim();
                zipcode = etZipcode.getText().toString().trim();
                tenantProfile.setAddressLine1(addressLine1);
                tenantProfile.setAddressLine2(addressLine2);
                tenantProfile.setZipcode(zipcode);
                cardAddressDetail.setVisibility(View.GONE);
                cardTerms.setVisibility(View.VISIBLE);
                tv_msg.setText("Ready to go!");
                break;
            case R.id.back2:
                cardAddressDetail.setVisibility(View.GONE);
                cardCountryDetail.setVisibility(View.VISIBLE);
                tv_msg.setText("Where you live?");
                break;
            case R.id.finish:
                tenantProfile.setEmail(emailId);
                tenantProfile.setPassword(password);
                mfire.registerNewTenant(tenantProfile);
                progressDialog.show();
                break;
            case R.id.back3:
                cardTerms.setVisibility(View.GONE);
                cardAddressDetail.setVisibility(View.VISIBLE);
                tv_msg.setText("Almost Done!");
                break;
            case R.id.btn_setup_profile:
                cardSetupProfile.setVisibility(View.GONE);
                cardNameDetail.setVisibility(View.VISIBLE);
                tv_msg.setText("Help us with your profile information.");
                break;

        }
    }

    private void validateName(String fName, String lName, String phone) {

        if (fName.isEmpty()) {
            invalidFname();
            return;
        }
        if (phone.length() < 10) {
            invalidPhone();
            return;
        }
        tenantProfile.setfName(fName);
        tenantProfile.setlName(lName);
        tenantProfile.setPhone(phone);
        cardCountryDetail.setVisibility(View.VISIBLE);
        cardNameDetail.setVisibility(View.GONE);
    }

    public void invalidFname() {
        next1.setEnabled(true);
        etFirstName.setError("First name can't be null");
    }

    public void invalidPhone() {
        next1.setEnabled(true);
        etPhone.setError("Invalid phone no");
    }

    @Override
    public void onSuccess(String firebaseUser) {
        progressDialog.dismiss();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SetupProfile.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();

    }


}
