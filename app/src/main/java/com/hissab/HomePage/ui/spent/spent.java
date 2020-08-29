package com.hissab.HomePage.ui.spent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.patient.Medicine;
import com.hissab.HomePage.ui.patient.Patient;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.productAdapter;
import com.hissab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class spent extends Fragment {
    private DatabaseReference mDatabase;
    LinearLayoutManager linearLayoutManager;
    spentAdapter spentAdapter;
    RecyclerView list_spent;
    List<Spent> spentList;
    String uid, name, price, medicine_name, date, pa_id, pid,medicine,pname;
    Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Spent");
        View root = inflater.inflate(R.layout.fragment_spent, container, false);
        ctx = getActivity();
        spentList = new ArrayList<>();
        list_spent = root.findViewById(R.id.list_spent);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product").child(uid);
        linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_spent.setHasFixedSize(true);
        list_spent.setLayoutManager(linearLayoutManager);
        getSpentList();
        return root;
    }

    private void getSpentList() {
        list_spent.setLayoutManager(new LinearLayoutManager(ctx));
//        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                spentList.clear();
//                Medicine medicine=snapshot.getValue(Medicine.class);
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    pa_id = dataSnapshot.child("pa_id").getValue().toString();
//                    price = dataSnapshot.child("selling_price").getValue().toString();
//                    date = dataSnapshot.child("date").getValue().toString();
//                    pid = dataSnapshot.child("pid").getValue().toString();
////                    medicine_name=dataSnapshot.child("name").getValue().toString();
//                    FirebaseDatabase.getInstance().getReference().child("Patient").child(uid).orderByChild("pa_id").equalTo(pa_id).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            String patient = snapshot.getValue(Patient.class).getName();
//                            for (DataSnapshot areaSnapshot1 : snapshot.getChildren()) {
//                                name = areaSnapshot1.getValue(Patient.class).getName();
//                                FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(pid).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                        for (DataSnapshot areaSnapshot1 : snapshot.getChildren()) {
//
//
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Medicine medicine=dataSnapshot.getValue(Medicine.class);
//                Toast.makeText(ctx, ""+medicine.getMid(), Toast.LENGTH_SHORT).show();
                    pa_id=medicine.getPa_id();
                    pid=medicine.getPid();
                    date=medicine.getDate();
                    price=medicine.getSelling_price();
                    FirebaseDatabase.getInstance().getReference().child("Patient").child(uid).orderByChild("pa_id").equalTo(pa_id).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Patient patient = snapshot.getValue(Patient.class);
                            name=patient.getName();
//                        Toast.makeText(ctx, name, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(pid).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            medicine_name = snapshot.getValue(Product.class).getName();
//                            Toast.makeText(ctx, medicine_name+" p "+price+" d "+date+" n "+name, Toast.LENGTH_SHORT).show();
                            spentList.add(new Spent(name, price, medicine_name, date));
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                        spentAdapter = new spentAdapter(ctx, spentList);
                        list_spent.setAdapter(spentAdapter);
                        spentAdapter.notifyDataSetChanged();
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