package com.shankar.itctest.product;

import com.shankar.itctest.product.data.ProductApi;
import com.shankar.itctest.product.data.ProductUrl;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductPresenter implements ProductContract.Presenter {
    private Retrofit retrofit;
    private ProductContract.View view;

    public ProductPresenter(ProductContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ProductUrl.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getProductList(int pageNumber, int pageSize) {
        view.showProgress();

        Observable.just(retrofit.create(ProductApi.class)).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<ProductModel> couponsObservable
                            = s.getProducts(pageNumber, pageSize).subscribeOn(Schedulers.io());


                    return Observable.concatArray(couponsObservable);
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError);

    }


    private void handleResults(ProductModel productList) {
        view.hideProgress();
        if (productList.statusCode == 200) {
            view.showProductList(productList);
            view.totalAvailableProduct(productList.totalProducts);
        } else {
            view.errorWhileLoadingData(productList.statusCode);
        }
    }

    private void handleError(Throwable t) {
        view.onErrorResponse(t);
    }

    @Override
    public void receivedTotalAvailableProducts(int totalProducts, int size) {
        if (size < totalProducts) {
            view.isProductsAvailable(true);
        } else {
            view.isProductsAvailable(false);
        }

    }
}
