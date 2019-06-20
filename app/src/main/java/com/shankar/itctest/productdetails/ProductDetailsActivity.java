package com.shankar.itctest.productdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shankar.itctest.R;
import com.shankar.itctest.product.ProductList;
import com.shankar.itctest.product.data.ProductUrl;

public class ProductDetailsActivity extends AppCompatActivity {

    public TextView productId;
    public TextView productName;
    public TextView longDescription;
    public TextView price;
    public ImageView prodImage;

    private ProductList productLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        productId = (TextView) findViewById(R.id.productId);
        productName = (TextView) findViewById(R.id.productName);
        longDescription = (TextView) findViewById(R.id.longDescription);
        price = (TextView) findViewById(R.id.price);
        prodImage = (ImageView) findViewById(R.id.prodImage);


        productLists = getIntent().getParcelableExtra("list");
        if (productLists != null) {
            setValueInViews();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setValueInViews() {
        if (productLists.price != null) productId.setText(productLists.productId);
        if (productLists.price != null) productName.setText(productLists.productName);
        if (productLists.price != null) longDescription.setText(Html.fromHtml(productLists.longDescription));
        if (productLists.price != null) price.setText(Html.fromHtml(productLists.price));
        if (productLists.price != null) Glide.with(this).load(ProductUrl.BASE_URL+productLists.productImage).into(prodImage);
    }
}
