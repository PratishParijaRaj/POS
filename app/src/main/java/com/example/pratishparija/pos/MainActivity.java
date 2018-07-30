package com.example.pratishparija.pos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratishparija.pos.database.FirebaseAuthenticationService;
import com.example.pratishparija.pos.models.TenantProfile;
import com.example.pratishparija.pos.models.UserRole;
import com.example.pratishparija.pos.models.UserRoleMapping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FirebaseAuthenticationService.FirebaseAuthenticationCallback {

    private FirebaseDatabase database;
    private FirebaseAuthenticationService mfire;
    private FirebaseAuthenticationService.FirebaseAuthenticationCallback mCallback;

    @BindView(R.id.fooo)
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
//        getTenantDetails();
//        insertTenantProfileData();
//        getUserRoleKey();
        mfire = new FirebaseAuthenticationService(mCallback);
        TenantProfile role = new TenantProfile();
        mfire.registerNewTenant(role);

    }

    protected void userMaping() {
        DatabaseReference roleReference = database.getReference("UserRoleMapping");
        UserRoleMapping role = new UserRoleMapping("#62,2nd cross", "pratishparija2@gmail.com");
        String id = roleReference.push().getKey();
        roleReference.child(id).setValue(role);
    }

    protected void insertTenantProfileData() {
        DatabaseReference roleReference = database.getReference("TenantProfile");
        TenantProfile role = new TenantProfile("#62,2nd cross", "pratishparija2@gmail.com", "Pratish", "Parija", "9090489902");
        String id = roleReference.push().getKey();
        roleReference.child(id).setValue(role);
    }

    protected void getUserRoleKey() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tenentRef = ref.child("Roles");

        tenentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    key = key.concat(" " + snapshot.getKey());
                }
                tvForgotPassword.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void getTenantDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tenentRef = ref.child("TenantProfile");

        tenentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    key = key.concat(" " + snapshot.getKey());
                }
                tvForgotPassword.setText(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSuccess(String firebaseUser) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

    }
}
