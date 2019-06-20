package com.shankar.itctest.product;

import com.shankar.itctest.BasePresenter;
import com.shankar.itctest.BaseView;

import java.util.ArrayList;

public interface ProductContract {

    interface View extends BaseView<Presenter> {

        void showProductList(ProductModel productModel);

        void onErrorResponse(Throwable t);

        void errorWhileLoadingData(int statusCode);

        void totalAvailableProduct(int totalProducts);

        void isProductsAvailable(boolean isDataAvailable);

        void hideProgress();

        void showProgress();
    }

    interface Presenter extends BasePresenter {

        void initializeRetrofit();
        void getProductList(int pageNumber, int pageSize);

        void receivedTotalAvailableProducts(int totalProducts, int size);
    }
}
