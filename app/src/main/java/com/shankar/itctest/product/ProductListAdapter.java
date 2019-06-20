package com.shankar.itctest.product;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shankar.itctest.R;
import com.shankar.itctest.product.data.ProductUrl;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<ProductList> productLists;
    private Context context;
    private ListItemClick listItemClick ;

    public ProductListAdapter(List<ProductList> productLists, Context ctx) {
        this.productLists = productLists;
        context = ctx;
    }

    public void initializeListener(ListItemClick listItemClick){
        this.listItemClick = listItemClick;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductList list = productLists.get(position);
        if (list.productId != null) holder.productId.setText(list.productId);
        if (list.productName != null) holder.productName.setText(list.productName);
        if (list.shortDescription != null) holder.shortDescription.setText(Html.fromHtml(list.shortDescription));
        if (list.price != null) holder.price.setText(list.price);
        if (list.price != null) Glide.with(context).load(ProductUrl.BASE_URL+list.productImage).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productId;
        public TextView productName;
        public TextView shortDescription;
        public TextView price;
        public ImageView productImage;

        public ViewHolder(View view) {
            super(view);

            productId = (TextView) view.findViewById(R.id.productId);
            productName = (TextView) view.findViewById(R.id.productName);
            shortDescription = (TextView) view.findViewById(R.id.shortDescription);
            price = (TextView) view.findViewById(R.id.price);
            productImage = (ImageView) view.findViewById(R.id.productImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listItemClick.onItemClick(getAdapterPosition());
        }
    }

    interface ListItemClick{
        void onItemClick(int pos);
    }
}
