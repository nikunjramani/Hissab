package com.hissab.HomePage.ui.Stoke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.productAdapter;
import com.hissab.R;

import java.util.ArrayList;
import java.util.List;

public class stokeAdapter extends RecyclerView.Adapter<stokeAdapter.ViewHolder> {

    Context ctx;
    List<Stoke> stokeList=new ArrayList<>();

    public stokeAdapter(Context ctx, List<Stoke> stokeList) {
        this.ctx = ctx;
        this.stokeList = stokeList;
    }

    @NonNull
    @Override
    public stokeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.list_stoke,parent,false);
        return new stokeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stokeAdapter.ViewHolder holder, int position) {
        final Stoke product=stokeList.get(position);
        holder.product_name.setText(product.getName());
        holder.product_actual_price.setText(product.getActual_price());
        holder.product_selling_price.setText(product.getSelling_price());
        holder.product_quentity.setText(product.getQuantity());
        holder.sp.setText(product.getSp());
        holder.date.setText(product.getDate());
    }

    @Override
    public int getItemCount() {
        return stokeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_actual_price,product_selling_price,product_quentity,edit_product,date,sp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=itemView.findViewById(R.id.product_name);
            product_actual_price=itemView.findViewById(R.id.product_actual_price);
            product_selling_price=itemView.findViewById(R.id.product_selling_price);
            product_quentity=itemView.findViewById(R.id.product_actual_quentity);
            edit_product=itemView.findViewById(R.id.edit_product);
            sp=itemView.findViewById(R.id.sp);
            date=itemView.findViewById(R.id.date);
        }
    }
}
