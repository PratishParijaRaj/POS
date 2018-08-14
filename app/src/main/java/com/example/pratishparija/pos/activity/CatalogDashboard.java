package com.example.pratishparija.pos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.pratishparija.pos.R;
import com.example.pratishparija.pos.models.CatalogTable;
import com.example.pratishparija.pos.models.CategoryTable;
import com.example.pratishparija.pos.models.ProductTable;
import com.example.pratishparija.pos.services.InsertProduct;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;

public class CatalogDashboard extends AppCompatActivity implements InsertProduct.ProductCallback {


    InsertProduct iproduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_dashboard);
        ButterKnife.bind(this);
        iproduct = new InsertProduct(this);
        ProductTable product = new ProductTable();
        product.setpName("Air");
        product.setpBrand("Nike");
        product.setpColour("white");
        product.setpHeight("34inch");
        iproduct.addProduct(product);
        CatalogTable catalogTable = new CatalogTable();
        catalogTable.setCatalogName("men shoes");
        catalogTable.setDescription("running");
        catalogTable.setValidFrom("10-12-2018");
        catalogTable.setValidThrough("10-12-2019");
        iproduct.addCatalog(catalogTable);
        CategoryTable categoryTable = new CategoryTable();
        categoryTable.setCategoryName("Shoes");
        categoryTable.setDescription("sports shoes");
        categoryTable.setValidFrom("10-12-2018");
        categoryTable.setValidThrough("10-12-2019");
        iproduct.addCategory(categoryTable);

    }


    @Override
    public void onSuccess(String firebaseUser) {
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onError(Exception e) {

    }
}
