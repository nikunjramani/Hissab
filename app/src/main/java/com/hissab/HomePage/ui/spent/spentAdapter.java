package com.hissab.HomePage.ui.spent;

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

public class spentAdapter extends RecyclerView.Adapter<spentAdapter.ViewHolder>  {
    Context ctx;
    List<Spent> spentList=new ArrayList<>();

    public spentAdapter(Context ctx, List<Spent> spentList) {
        this.ctx = ctx;
        this.spentList = spentList;
    }

    @NonNull
    @Override
    public spentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.list_spent,parent,false);
        return new spentAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull spentAdapter.ViewHolder holder, int position) {
        final Spent spent=spentList.get(position);
        holder.date.setText(spent.getDate());
        holder.name.setText(spent.getName());
        holder.medicine_name.setText(spent.getMedicine_name());
        holder.price.setText(spent.getPrice());
    }

    @Override
    public int getItemCount() {
        return spentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView name,price,medicine_name,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            medicine_name=itemView.findViewById(R.id.medicine_name);
            date=itemView.findViewById(R.id.date);
        }
    }
}
