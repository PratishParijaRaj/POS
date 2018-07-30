package com.example.pratishparija.pos.database;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.pratishparija.pos.models.TenantProfile;
import com.example.pratishparija.pos.models.UserRole;
import com.example.pratishparija.pos.models.UserRoleMapping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public class FirebaseAuthenticationService implements Executor {

    private static final String TAG = FirebaseAuthenticationService.class.toString();
    private FirebaseAuthenticationCallback mCallback;
    private FirebaseAuth mAuth;

    public FirebaseAuthenticationService(FirebaseAuthenticationCallback callback) {

        if (callback != null)
            mCallback = callback;

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerNewTenant(TenantProfile tenant) {

        if (tenant != null) {

            try {
                TenantProfile tenantProfile = new TenantProfile();
//                String email = tenantProfile.getEmail();
                String email = "pparijaraj1996@gmail.com";
                String password = "123456789";

                createNewUserWithEmailAndPassword(email, password, tenantProfile);
            } catch (final Exception e) {

                if (mCallback instanceof Activity) {
                    ((Activity) mCallback).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onError(e);
                        }
                    });
                }
            }
        }
    }

    private void createNewUserWithEmailAndPassword(final String email, String password, final TenantProfile tenantProfile) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(FirebaseAuthenticationService.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();


                            if (user != null) {
                                createTenant(user.getUid(), tenantProfile);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if (mCallback instanceof Activity) {
                                ((Activity) mCallback).runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void run() {
                                        mCallback.onFailure(Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                });
                            }
                        }
                    }
                });
    }


    private void createTenant(final String tenantUid, TenantProfile tenantProfile) {

        tenantProfile.setCreatedDate(Calendar.getInstance().getTimeInMillis());
        tenantProfile.setLastModifiedDate(tenantProfile.getCreatedDate());

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("TenantProfile").child(tenantUid).setValue(tenantProfile)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Create Super admin user role for new tenant
                        createUserRole(tenantUid, UserRole.ROLE_SUPER_ADMIN);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        if (mCallback instanceof Activity) {
                            ((Activity) mCallback).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mCallback.onError(e);
                                }
                            });
                        }
                    }
                });
    }

    private void createUserRole(final String tenantUid, final String type) {

        final UserRole[] userRole = {null};
        if (type.equalsIgnoreCase(UserRole.ROLE_SUPER_ADMIN)) {

            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("Roles").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        userRole[0] = snapshot.getValue(UserRole.class);
                        if (userRole[0] != null &&
                                userRole[0].getType().equalsIgnoreCase(type)) {

                            Log.d(TAG, "onDataChange: " + userRole[0].getRoleId());
                            userRole[0].setRoleId(snapshot.getKey());

                            //Assign user role to the super admin
                            assignTenantRole(tenantUid, userRole[0]);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull final DatabaseError databaseError) {

                    if (mCallback instanceof Activity) {
                        ((Activity) mCallback).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onError(databaseError.toException());
                            }
                        });
                    }
                }
            });
        }
    }

    private void assignTenantRole(final String tenantUid, UserRole role) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        UserRoleMapping roleMapping = new UserRoleMapping();
        roleMapping.setUserId(tenantUid);
        roleMapping.setRoleId(role.getRoleId());

        database.child("UserRoleMapping").child(UUID.randomUUID().toString()).setValue(roleMapping)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {

                        if (task.isComplete()) {

                            if (mCallback instanceof Activity) {
                                ((Activity) mCallback).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCallback.onSuccess(tenantUid);
                                    }
                                });
                            }
                        } else {

                            if (mCallback instanceof Activity) {
                                ((Activity) mCallback).runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void run() {
                                        mCallback.onFailure(Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private void signInUserWithEmailAndPassword(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            if (mCallback instanceof Activity) {
                                ((Activity) mCallback).runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void run() {
                                        mCallback.onSuccess(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                    }
                                });
                            }
                        } else {
                            if (mCallback instanceof Activity) {
                                ((Activity) mCallback).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCallback.onError(task.getException());
                                    }
                                });
                            }
                        }
                    }
                });
    }

    public void authenticateExistingTenantWithEmailAndPassword(TenantProfile tenant) {

        //Check if user is signed in (non-null) and update UI accordingly.
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            if (tenant != null) {

                try {
                    TenantProfile tenantProfile = new TenantProfile();
                    String email = tenantProfile.getEmail();
                    String password = tenantProfile.getPassword();

                    signInUserWithEmailAndPassword(email, password);
                } catch (final Exception e) {

                    if (mCallback instanceof Activity) {
                        ((Activity) mCallback).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onError(e);
                            }
                        });
                    }
                }
            }
        } else {

            if (mCallback instanceof Activity) {
                ((Activity) mCallback).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onSuccess(currentUser.getUid());
                    }
                });
            }
        }
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }

    public interface FirebaseAuthenticationCallback {

        void onSuccess(String firebaseUser);

        void onFailure(String message);

        void onError(Exception e);
    }
}
