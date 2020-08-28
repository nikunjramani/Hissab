package com.hissab.HomePage.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.product;
import com.hissab.R;
import com.hissab.staticValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class patient_details extends Fragment {

    private ArrayList<String> productList;
    String pid=null,pa_id=null;
    static Boolean userexist =false;
    TextView medicine_actual_price;
    TextInputEditText patient_age,patient_number,patient_address,medicine_quentity,medicine_selling_price;
    Spinner medicine_name;
    Button submit;
    int quantity=0;
    MaterialAutoCompleteTextView patient_name;
    String uid,date;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_patient_view, container, false);
        medicine_actual_price=root.findViewById(R.id.medicine_actual_price);
        patient_address=root.findViewById(R.id.patient_address);
        patient_age=root.findViewById(R.id.patient_age);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        patient_number=root.findViewById(R.id.patient_number);
        patient_name=root.findViewById(R.id.patient_name);
        medicine_quentity=root.findViewById(R.id.medicine_quentity);
        medicine_selling_price=root.findViewById(R.id.medicine_selling_price);
        medicine_name=root.findViewById(R.id.medicine_name);
        submit=root.findViewById(R.id.submit);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product").child(uid);
        autoCompleteName();
        fetch();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Integer.parseInt(medicine_quentity.getText().toString())<quantity){
//                    medicine_quentity.setError("Out Of Quantity");
//                }
                if (userexist) {
                    Toast.makeText(getActivity(), "true"+pa_id, Toast.LENGTH_SHORT).show();
                    final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference();
                    Map<String, Object> map = new HashMap<>();
                    map.put("Patient/"+uid+"/"+pa_id,new Patient(patient_address.getText().toString(),patient_age.getText().toString(),patient_name.getText().toString(),patient_number.getText().toString(),String.valueOf(staticValue.getPa_id()),uid));
                    updateData.updateChildren(map);

                    //                add Medicine Details
                    Map<String, Object> medicine = new HashMap<>();
                    medicine.put("mid", String.valueOf(staticValue.getMid()));
                    medicine.put("udi", uid);
                    medicine.put("pid", pid);
                    medicine.put("quentity", medicine_quentity.getText().toString());
                    medicine.put("selling_price",medicine_selling_price.getText().toString());
                    medicine.put("pa_id", staticValue.getPa_id());
                    medicine.put("date", date);
                    DatabaseReference medi = FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).child(String.valueOf(staticValue.getMid()));
                    medi.setValue(medicine);
                    staticValue.setMid(Integer.parseInt(staticValue.getMid()) + 1);

                    userexist=false;
                } else if(!userexist) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pa_id", staticValue.getPa_id());
                    map.put("uid", uid);
                    map.put("name", patient_name.getText().toString());
                    map.put("age", patient_age.getText().toString());
                    map.put("address", patient_address.getText().toString());
                    map.put("number", patient_number.getText().toString());
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Patient").child(uid).child(String.valueOf(staticValue.getPa_id()));
                    data.setValue(map);

                    //                add Medicine Details
                    Map<String, Object> medicine = new HashMap<>();
                    medicine.put("mid", staticValue.getMid());
                    medicine.put("udi", uid);
                    medicine.put("pid", pid);
                    medicine.put("quentity", medicine_quentity.getText().toString());
                    medicine.put("selling_price",medicine_selling_price.getText().toString());
                    medicine.put("pa_id", staticValue.getPa_id());
                    medicine.put("date", date);
                    DatabaseReference medi = FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).child(String.valueOf(staticValue.getMid()));
                    medi.setValue(medicine);
                    staticValue.setMid(Integer.parseInt(staticValue.getMid()) + 1);
                    staticValue.setPa_id(Integer.parseInt(staticValue.getPa_id()) + 1);

                }

//                update Product details
                final DatabaseReference updateQuantity = FirebaseDatabase.getInstance().getReference().child("Product").child(uid).child(pid);
                Map<String, Object> map = new HashMap<>();
                map.put("quentity",String.valueOf(quantity-Integer.parseInt(medicine_quentity.getText().toString())));
                updateQuantity.updateChildren(map);
                getFragmentManager().beginTransaction().detach(patient_details.this).commit();
                getFragmentManager().beginTransaction().attach(patient_details.this).commit();
            }
        });
        return root;
    }

    private void autoCompleteName(){
        DatabaseReference data=FirebaseDatabase.getInstance().getReference().child("Patient").child(uid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> nomeConsulta = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    String consultaName = areaSnapshot.getValue(Patient.class).getName();
                    nomeConsulta.add(consultaName);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, nomeConsulta);
                patient_name.setThreshold(1);
                patient_name.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        patient_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userexist=true;
                String str = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("Patient").child(uid).orderByChild("name").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                medicine_selling_price.setText(issue.getValue(Product.class).getSellingPrice());
                                medicine_actual_price.setText(issue.getValue(Product.class).getActualPrice());
                                pid=issue.getValue(Product.class).getPid();
                                patient_name.setText(issue.getValue(Patient.class).getName());
                                patient_address.setText(issue.getValue(Patient.class).getAddress());
                                patient_age.setText(issue.getValue(Patient.class).getAge());
                                patient_number.setText(issue.getValue(Patient.class).getNumber());
                                pa_id=issue.getValue(Patient.class).getPa_id();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void fetch() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> nomeConsulta = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    String consultaName = areaSnapshot.getValue(Product.class).getName();
                    nomeConsulta.add(consultaName);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nomeConsulta);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                medicine_name.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        medicine_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String medicine_name=parent.getItemAtPosition(position).toString();
                Query query = mDatabase.orderByChild("name").equalTo(medicine_name);;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot issue : snapshot.getChildren()) {
                                medicine_selling_price.setText(issue.getValue(Product.class).getSellingPrice());
                                medicine_actual_price.setText(issue.getValue(Product.class).getActualPrice());
                                pid=issue.getValue(Product.class).getPid();
                                quantity=Integer.parseInt(issue.getValue(Product.class).getQuentity());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}