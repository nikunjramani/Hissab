package com.hissab.HomePage.ui.product;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.R;
import com.hissab.staticValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class product extends Fragment {

    private productAdapter allDataAdapter;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<Product> mUserList = new ArrayList<>();
    Context ctx;
    String uid;
    CardView add_product;
    RecyclerView product_list;
    LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_product, container, false);
        ctx=getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Product");
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase =FirebaseDatabase.getInstance().getReference().child("Product").child(uid);
        add_product=root.findViewById(R.id.add_product);
        product_list=root.findViewById(R.id.product_list);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText product_name,product_quentity,actual_price,selling_price,sp;
                View view = View.inflate(ctx, R.layout.add_product, null);
                product_name = view.findViewById(R.id.product_name);
                actual_price = view.findViewById(R.id.actual_price);
                selling_price = view.findViewById(R.id.selling_price);
                sp = view.findViewById(R.id.product_sp);
                product_quentity = view.findViewById(R.id.product_quentity);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setView(view);
                alert.setTitle("Add Product");
                alert.setPositiveButton("Add Product", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Map<String, Object> map = new HashMap<>();
                            mDatabase.child(staticValue.getPid()).setValue(new Product(actual_price.getText().toString(),date,product_name.getText().toString(),staticValue.getPid(),product_quentity.getText().toString(),sp.getText().toString(),selling_price.getText().toString()));
                        FirebaseDatabase.getInstance().getReference().child("Stoke").child(uid).child(staticValue.getSid()).setValue(new Stoke(staticValue.getSid(),staticValue.getPid(),product_quentity.getText().toString(),sp.getText().toString(),date));
                        staticValue.setPid(Integer.parseInt(staticValue.getPid())+1);
                        staticValue.setSid(Integer.parseInt(staticValue.getSid())+1);
                        getFragmentManager().beginTransaction().detach(product.this).commit();
                        getFragmentManager().beginTransaction().attach(product.this).commit();
                    }
                });
                alert.show();
            }
        });

        linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        product_list.setHasFixedSize(true);
        product_list.setLayoutManager(linearLayoutManager);
        fetch();
        return root;
    }

    private void fetch() {

        product_list.setLayoutManager(new LinearLayoutManager(ctx));
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    Product user = dataSnapshot1.getValue(Product.class);
                    mUserList.add(user);
                }
                allDataAdapter = new productAdapter(ctx, mUserList);
                product_list.setAdapter(allDataAdapter);
                allDataAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}