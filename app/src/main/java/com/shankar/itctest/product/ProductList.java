package com.shankar.itctest.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductList implements Parcelable {
    @SerializedName("productId")
    public String productId;
    @SerializedName("productName")
    public String productName;
    @SerializedName("shortDescription")
   public String shortDescription;
    @SerializedName("longDescription")
   public String longDescription;
    @SerializedName("price")
   public String price;
    @SerializedName("productImage")
    public String productImage;
    @SerializedName("reviewRating")
    public String reviewRating;
    @SerializedName("reviewCount")
   public String reviewCount;
    @SerializedName("inStock")
    public String inStock;

    protected ProductList(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
        price = in.readString();
        productImage = in.readString();
        reviewRating = in.readString();
        reviewCount = in.readString();
        inStock = in.readString();
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel in) {
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(price);
        dest.writeString(productImage);
        dest.writeString(reviewRating);
        dest.writeString(reviewCount);
        dest.writeString(inStock);
    }
}
