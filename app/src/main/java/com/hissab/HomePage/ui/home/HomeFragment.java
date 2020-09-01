package com.hissab.HomePage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.patient.Medicine;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.Stoke;
import com.hissab.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {
    int total_sp=0,today_income_sum = 0,total_profite_sum = 0,total_income_sum = 0,today_profite_sum=0;
    String uid,date;
    TextView today_income,today_profite,total_income,total_profite,sp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        today_income=root.findViewById(R.id.today_income);
        total_profite=root.findViewById(R.id.total_profite);
        today_profite=root.findViewById(R.id.today_profite);
        total_income=root.findViewById(R.id.total_income);
        sp=root.findViewById(R.id.total_sp);
        getData();
        return root;
    }

    private void getData() {
       DatabaseReference data=FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    total_income_sum =total_income_sum + (Integer.parseInt(areaSnapshot.getValue(Medicine.class).getSelling_price())*Integer.parseInt(areaSnapshot.getValue(Medicine.class).getQuentity()));
                }
                if(total_income_sum!=0){
                    total_income.setText(String.valueOf(total_income_sum));
                }else {
                    total_income.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference profite = FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid);
        profite.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    final int quantity=Integer.parseInt(areaSnapshot.getValue(Medicine.class).getQuentity());
                    String str =areaSnapshot.getValue(Medicine.class).getPid();
                    FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                                total_profite_sum =total_profite_sum +(Integer.parseInt(areaSnapshot.getValue(Product.class).getActualPrice())*quantity);
                            }
                            if (total_profite_sum!=0){
                                total_profite.setText((total_income_sum-total_profite_sum)+"");
                            }else {
                                total_profite.setText("0");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).orderByChild("date").equalTo(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    today_income_sum =today_income_sum +( Integer.parseInt(areaSnapshot.getValue(Medicine.class).getSelling_price())*Integer.parseInt(areaSnapshot.getValue(Medicine.class).getQuentity()));
                }
                if(today_income_sum!=0) {
                    today_income.setText(today_income_sum+"");
                }else {
                    today_income.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).orderByChild("date").equalTo(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    final int quantity=Integer.parseInt(areaSnapshot.getValue(Medicine.class).getQuentity());
                    String str =areaSnapshot.getValue(Medicine.class).getPid();
                    FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot areaSnapshot1 : snapshot1.getChildren()) {
                                today_profite_sum =(today_profite_sum + Integer.parseInt(areaSnapshot1.getValue(Product.class).getActualPrice())*quantity);
                            }
                            if(today_profite_sum !=0) {
                                today_profite.setText((today_income_sum - today_profite_sum)+"");
                            }else {
                                today_profite.setText("0");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Stoke").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    int quantity=Integer.parseInt(areaSnapshot.getValue(Stoke.class).getSp());
                    total_sp +=quantity;
                }
                sp.setText(String.valueOf(total_sp));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}