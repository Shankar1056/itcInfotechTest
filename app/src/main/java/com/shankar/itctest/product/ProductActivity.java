package com.shankar.itctest.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shankar.itctest.R;
import com.shankar.itctest.productdetails.ProductDetailsActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements ProductContract.View, ProductListAdapter.ListItemClick {

    private static final int pageSize = 10;
    private ProductContract.Presenter presenter;
    private RecyclerView productRv;
    private int pageNumber;
    private ArrayList<ProductList> productLists;
    private ProductListAdapter productListAdapter;
    private boolean isLoading = true;
    private TextView productCount;
    private AVLoadingIndicatorView progressAVL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productRv = findViewById(R.id.product_rv);
        productRv.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        new ProductPresenter(ProductActivity.this);

        productLists = new ArrayList<>();
        productListAdapter = new ProductListAdapter(productLists, ProductActivity.this);
        productRv.setAdapter(productListAdapter);
        productListAdapter.initializeListener(this);

        productCount = findViewById(R.id.productCount);
        progressAVL = findViewById(R.id.progressAVL);

        pageNumber = 1;
        presenter.initializeRetrofit();
        loadProducts();
        initScrollListener();

    }

    private void loadProducts() {
        presenter.getProductList(pageNumber, pageSize);
    }

    private void initScrollListener() {
        productRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productLists.size() - 1) {
                        //bottom of list!
                        pageNumber = pageNumber+1;
                        loadProducts();
                        isLoading = false;
                    }
                }
            }
        });

    }


    @Override
    public void showProductList(ProductModel productModel) {
        productLists.addAll(productModel.products);
        productListAdapter.notifyDataSetChanged();
        productCount.setText(""+productLists.size()+"/"+productModel.totalProducts);
        Toast.makeText(this, "Current size: "+productModel.products.size()+"total size: "+productLists.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void totalAvailableProduct(int totalProducts) {
        presenter.receivedTotalAvailableProducts(totalProducts, productLists.size());
    }

    @Override
    public void isProductsAvailable(boolean isDataAvailable) {
        isLoading = isDataAvailable;
    }

    @Override
    public void hideProgress() {
        progressAVL.hide();
    }

    @Override
    public void showProgress() {
        progressAVL.show();
    }

    @Override
    public void onErrorResponse(Throwable t) {
        Toast.makeText(this, "Something went wrong:: Error is - " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorWhileLoadingData(int statusCode) {
        Toast.makeText(this, "Error while loading data from server:: Error code - " + statusCode, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(ProductContract.Presenter Presenter) {
        this.presenter = Presenter;
    }

    @Override
    public void onItemClick(int pos) {
        startActivity(new Intent(this, ProductDetailsActivity.class)
        .putExtra("list", productLists.get(pos)));
    }
}
