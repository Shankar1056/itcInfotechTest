package com.shankar.itctest.product;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductModel {


    @SerializedName("statusCode")
    int statusCode;
    @SerializedName("pageSize")
    int pageSiz;
    @SerializedName("pageNumber")
    int pageNumber;
    @SerializedName("totalProducts")
    int totalProducts;
    @SerializedName("products")
    ArrayList<ProductList> products;
}



