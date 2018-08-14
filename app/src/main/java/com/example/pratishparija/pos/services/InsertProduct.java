package com.example.pratishparija.pos.services;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.pratishparija.pos.models.CatalogTable;
import com.example.pratishparija.pos.models.CategoryTable;
import com.example.pratishparija.pos.models.ProductTable;
import com.example.pratishparija.pos.models.UserRole;
import com.example.pratishparija.pos.utils.MyPreferense;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.Executor;

public class InsertProduct implements Executor {
    ProductCallback pCallback;
    FirebaseAuth mAuth;

    public void addProduct(ProductTable product) {
        if (product != null) {

            try {
                product.setCreatedOn(Calendar.getInstance().getTimeInMillis());
                product.setLastModifiedDate(product.getCreatedOn());
                product.setLastModifiedBy(MyPreferense.getUserId());
                product.setCreatedBy(MyPreferense.getUserId());

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Products");

                String id = database.push().getKey();

                database.child(id).setValue(product)
                        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                final FirebaseUser user = mAuth.getCurrentUser();
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onSuccess("Done");
                                        }
                                    });
                                }

                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull final Exception e) {
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onError(e);
                                        }
                                    });
                                }
                            }
                        });


            } catch (final Exception e) {

                {
                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pCallback.onError(e);
                        }
                    });
                }
            }
        }

    }

    public void addCategory(CategoryTable category) {
        if (category != null) {

            try {
                category.setCreatedDate(Calendar.getInstance().getTimeInMillis());
                category.setLastModifiedDate(category.getCreatedDate());
                category.setLastModifiedBy(MyPreferense.getUserId());
                category.setCreatedBy(MyPreferense.getUserId());

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Categories");

                String id = database.push().getKey();

                database.child(id).setValue(category)
                        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onSuccess("Done");
                                        }
                                    });
                                }

                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull final Exception e) {
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onError(e);
                                        }
                                    });
                                }
                            }
                        });


            } catch (final Exception e) {

                {
                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pCallback.onError(e);
                        }
                    });
                }
            }
        }

    }

    public void addCatalog(CatalogTable catalog) {
        if (catalog != null) {

            try {
                catalog.setCreatedDate(Calendar.getInstance().getTimeInMillis());
                catalog.setLastModifiedDate(catalog.getCreatedDate());
                catalog.setLastModifiedBy(MyPreferense.getUserId());
                catalog.setCreatedBy(MyPreferense.getUserId());

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Catalog");

                String id = database.push().getKey();

                database.child(id).setValue(catalog)
                        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onSuccess("Done");
                                        }
                                    });
                                }

                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull final Exception e) {
                                if (pCallback instanceof Activity) {
                                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pCallback.onError(e);
                                        }
                                    });
                                }
                            }
                        });


            } catch (final Exception e) {

                {
                    ((Activity) pCallback).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pCallback.onError(e);
                        }
                    });
                }
            }
        }

    }

    public InsertProduct(ProductCallback callback) {

        if (callback != null)
            pCallback = callback;

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void execute(@NonNull Runnable runnable) {

    }

    public interface ProductCallback {

        void onSuccess(String firebaseUser);

        void onFailure(String message);

        void onError(Exception e);
    }
}
