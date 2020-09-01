package com.hissab.HomePage.ui.Stoke;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.productAdapter;
import com.hissab.HomePage.ui.spent.spentAdapter;
import com.hissab.R;

import java.util.ArrayList;
import java.util.List;

public class stoke extends Fragment {

    private List<Stoke> stokeList = new ArrayList<>();
    Context ctx;
    String uid;
    stokeAdapter stokeAdapter;
    private DatabaseReference mDatabase;
    RecyclerView stoke_list;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_stoke, container, false);
        ctx=getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Stoke");
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Stoke").child(uid);
        stoke_list=view.findViewById(R.id.recycler_view_stoke);
        linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stoke_list.setHasFixedSize(true);
        stoke_list.setLayoutManager(linearLayoutManager);
        fetch();
        return view;
    }
    private void fetch() {

        stoke_list.setLayoutManager(new LinearLayoutManager(ctx));
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stokeList.clear();
                for (final DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(String.valueOf(dataSnapshot1.child("pid").getValue())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot Snapshot1 : snapshot.getChildren()) {
//                                Toast.makeText(getActivity(), dataSnapshot1.child("pid").getValue().toString(), Toast.LENGTH_SHORT).show();
                                stokeList.add(new Stoke(dataSnapshot1.child("pid").getValue().toString(), dataSnapshot1.child("quantity").getValue().toString(), dataSnapshot1.child("sp").getValue().toString(), dataSnapshot1.child("date").getValue().toString(), Snapshot1.child("name").getValue().toString(), Snapshot1.child("actualPrice").getValue().toString(), Snapshot1.child("sellingPrice").getValue().toString()));

                            }
//                            Toast.makeText(getActivity(), ""+snapshot, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Toast.makeText(ctx, "add adapter", Toast.LENGTH_SHORT).show();
                        stokeAdapter = new stokeAdapter(ctx, stokeList);
                        stoke_list.setAdapter(stokeAdapter);
                        stokeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}