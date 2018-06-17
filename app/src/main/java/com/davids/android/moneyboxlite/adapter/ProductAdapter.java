package com.davids.android.moneyboxlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davids.android.moneyboxlite.R;
import com.davids.android.moneyboxlite.model.product.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> mList;
    Context mContext;
    OnProductClickListener mListener;

    public interface OnProductClickListener{
        void onProductClick(int pos);
    }

    public ProductAdapter(Context context, List<Product> list, OnProductClickListener listener){
        mContext = context;
        mList = list;
        mListener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Product product = mList.get(position);

        holder.productName.setText(product.getProduct().getFriendlyName());
        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProductClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout productLayout;
        TextView productName;
        public ViewHolder(View itemView) {
            super(itemView);
            productLayout = itemView.findViewById(R.id.product_item_layout);
            productName = itemView.findViewById(R.id.name_of_account);
        }
    }
}
