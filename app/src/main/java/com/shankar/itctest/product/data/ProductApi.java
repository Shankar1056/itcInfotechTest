package com.shankar.itctest.product.data;

import com.shankar.itctest.product.ProductModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApi {

    @GET("walmartproducts/{pageNumber}/{pageSize}")
    Observable<ProductModel> getProducts(@Path("pageNumber") int pageNumber, @Path("pageSize") int pageSize);


}
