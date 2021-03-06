package com.hissab.HomePage.ui.product;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hissab.R;
import com.hissab.staticValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    Context ctx;
    List<Product> productList=new ArrayList<>();

    public productAdapter(Context ctx, List<Product> productList) {
        this.ctx = ctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(ctx).inflate(R.layout.list_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ViewHolder holder, int position) {
        final Product product=productList.get(position);
        holder.product_name.setText(product.getName());
        holder.date.setText(product.getDate());
        holder.product_actual_price.setText(product.getActualPrice());
        holder.product_selling_price.setText(product.getSellingPrice());
        holder.product_quentity.setText(product.getQuentity());
        holder.edit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText product_name,product_quentity,actual_price,sp,selling_price;
                final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                final Button setdate;
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                View view = View.inflate(ctx, R.layout.add_product, null);
                product_name = view.findViewById(R.id.product_name);
                actual_price = view.findViewById(R.id.actual_price);
                selling_price = view.findViewById(R.id.selling_price);
                sp = view.findViewById(R.id.product_sp);
                setdate=view.findViewById(R.id.setDate);
                product_quentity = view.findViewById(R.id.product_quentity);
                product_name.setText(product.getName());
                sp.setText(product.getSp());
                setdate.setText(product.getDate());
                actual_price.setText(product.getActualPrice());
                selling_price.setText(product.getSellingPrice());
                product_quentity.setText(product.getQuentity());
                final Calendar calendar=Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        setdate.setText(sdf.format(calendar.getTime()));
                    }
                };
                setdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(ctx, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                alert.setView(view);
                alert.setTitle("Edit Product");
                alert.setPositiveButton("Edit Product", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Map<String, Object> map = new HashMap<>();
                        map.put("Product/"+uid+"/"+product.getPid(),new Product(actual_price.getText().toString(),setdate.getText().toString(),product_name.getText().toString(),product.getPid(),product_quentity.getText().toString(),sp.getText().toString(),selling_price.getText().toString()));
                        mDatabase.updateChildren(map);
                        int q=Integer.parseInt(product_quentity.getText().toString())-Integer.parseInt(product.getQuentity());
                        FirebaseDatabase.getInstance().getReference().child("Stoke").child(uid).child(staticValue.getSid()).setValue(new Stoke(staticValue.getSid(),product.getPid(),String.valueOf(q),sp.getText().toString(),date));
                        staticValue.setSid(Integer.parseInt(staticValue.getSid())+1);
                        AppCompatActivity activity = (AppCompatActivity) ctx;
                        Fragment myFragment = new product();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_actual_price,product_selling_price,product_quentity,edit_product,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=itemView.findViewById(R.id.product_name);
            product_actual_price=itemView.findViewById(R.id.product_actual_price);
            product_selling_price=itemView.findViewById(R.id.product_selling_price);
            product_quentity=itemView.findViewById(R.id.product_actual_quentity);
            edit_product=itemView.findViewById(R.id.edit_product);
            date=itemView.findViewById(R.id.date);
        }
    }
}
