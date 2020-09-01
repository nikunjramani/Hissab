package com.hissab.HomePage.ui.patient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.product;
import com.hissab.HomePage.ui.spent.Spent;
import com.hissab.HomePage.ui.spent.spentAdapter;
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
    String pid=null,pa_id=null,pname,ppid,ppa_id,pdate,pprice,pmedicine_name;
    static Boolean userexist =false;
    TextView medicine_actual_price,medicine_available_quentity;
    TextInputEditText patient_age,patient_number,patient_address,medicine_quentity,medicine_selling_price;
    Spinner medicine_name;
    Button submit;
    RecyclerView list_pspent;
    CardView c1;
    int quantity=0;
    MaterialAutoCompleteTextView patient_name;
    String uid,date;
    private DatabaseReference mDatabase;
    LinearLayoutManager linearLayoutManager;
    com.hissab.HomePage.ui.spent.spentAdapter spentAdapter;
    List<Spent> spentList;
    Context ctx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_patient_view, container, false);
        ctx=getActivity();
        c1=root.findViewById(R.id.c1);
        c1.setVisibility(View.GONE);
        medicine_actual_price=root.findViewById(R.id.medicine_actual_price);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Patient Details");
        patient_address=root.findViewById(R.id.patient_address);
        patient_age=root.findViewById(R.id.patient_age);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        patient_number=root.findViewById(R.id.patient_number);
        patient_name=root.findViewById(R.id.patient_name);
        medicine_quentity=root.findViewById(R.id.medicine_quentity);
        medicine_selling_price=root.findViewById(R.id.medicine_selling_price);
        medicine_available_quentity=root.findViewById(R.id.medicine_available_quentity);
        medicine_name=root.findViewById(R.id.medicine_name);
        submit=root.findViewById(R.id.submit);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product").child(uid);
        autoCompleteName();
        fetch();
        list_pspent=root.findViewById(R.id.list_pspent);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Integer.parseInt(medicine_quentity.getText().toString())<quantity){
//                    medicine_quentity.setError("Out Of Quantity");
//                }
                if (Integer.parseInt(String.valueOf(medicine_quentity.getText())) > quantity) {
                    medicine_quentity.setError("Out Of Quantity");
                } else {
                    if (userexist) {
                        staticValue.setMid(Integer.parseInt(staticValue.getMid()) + 1);
                        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Patient/" + uid + "/" + staticValue.getPa_id(), new Patient(patient_address.getText().toString(), patient_age.getText().toString(), patient_name.getText().toString(), patient_number.getText().toString(), staticValue.getPa_id(), uid));
                        updateData.updateChildren(map);

                        //                add Medicine Details
                        Map<String, Object> medicine = new HashMap<>();
                        medicine.put("mid", String.valueOf(staticValue.getMid()));
                        medicine.put("udi", uid);
                        medicine.put("pid", pid);
                        medicine.put("quentity", medicine_quentity.getText().toString());
                        medicine.put("selling_price", medicine_selling_price.getText().toString());
                        medicine.put("pa_id", staticValue.getPa_id());
                        medicine.put("date", date);
                        DatabaseReference medi = FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).child(String.valueOf(staticValue.getMid()));
                        medi.setValue(medicine);

                        userexist = false;
                    } else if (!userexist) {
                        staticValue.setMid(Integer.parseInt(staticValue.getMid()) + 1);
                        staticValue.setPa_id(Integer.parseInt(staticValue.getPa_id()) + 1);
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
                        medicine.put("selling_price", medicine_selling_price.getText().toString());
                        medicine.put("pa_id", staticValue.getPa_id());
                        medicine.put("date", date);
                        DatabaseReference medi = FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).child(String.valueOf(staticValue.getMid()));
                        medi.setValue(medicine);

                    }
                    Toast.makeText(getActivity(), "Data Add Successfully", Toast.LENGTH_SHORT).show();

//                update Product details
                    final DatabaseReference updateQuantity = FirebaseDatabase.getInstance().getReference().child("Product").child(uid).child(pid);
                    Map<String, Object> map = new HashMap<>();
                    map.put("quentity", String.valueOf(quantity - Integer.parseInt(medicine_quentity.getText().toString())));
                    updateQuantity.updateChildren(map);
                    getFragmentManager().beginTransaction().detach(patient_details.this).commit();
                    getFragmentManager().beginTransaction().attach(patient_details.this).commit();
                }
            }
        });
        return root;
    }

    private void checkMedicalRecord(final String pid, final String pa_id) {
        spentList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_pspent.setHasFixedSize(true);
        list_pspent.setLayoutManager(linearLayoutManager);

//        Toast.makeText(ctx, "1", Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).orderByChild("pa_id").equalTo(pa_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    c1.setVisibility(View.VISIBLE);
                    getMedicalRecord(pid,pa_id);

                } else {
                    c1.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getMedicalRecord(String pid,String pa_id){
//        Toast.makeText(ctx, "2", Toast.LENGTH_SHORT).show();
        list_pspent.setLayoutManager(new LinearLayoutManager(ctx));
        FirebaseDatabase.getInstance().getReference().child("Medicine").child(uid).orderByChild("pa_id").equalTo(pa_id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Medicine medicine=dataSnapshot.getValue(Medicine.class);
//                Toast.makeText(ctx, ""+medicine.getMid(), Toast.LENGTH_SHORT).show();
                    ppa_id=medicine.getPa_id();
                    ppid=medicine.getPid();
                    pdate=medicine.getDate();
                    pprice=medicine.getSelling_price();
                    FirebaseDatabase.getInstance().getReference().child("Patient").child(uid).orderByChild("pa_id").equalTo(ppa_id).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Patient patient = snapshot.getValue(Patient.class);
                            pname=patient.getName();
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
                    FirebaseDatabase.getInstance().getReference().child("Product").child(uid).orderByChild("pid").equalTo(ppid).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            pmedicine_name = snapshot.getValue(Product.class).getName();
//                            Toast.makeText(ctx, medicine_name+" p "+price+" d "+date+" n "+name, Toast.LENGTH_SHORT).show();
                            spentList.add(new Spent(pname, pprice, pmedicine_name, pdate));
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
                        list_pspent.setAdapter(spentAdapter);
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
                                String id,paa_id;
                                medicine_selling_price.setText(issue.getValue(Product.class).getSellingPrice());
                                medicine_actual_price.setText(issue.getValue(Product.class).getActualPrice());
                                id=issue.getValue(Product.class).getPid();
                                patient_name.setText(issue.getValue(Patient.class).getName());
                                patient_address.setText(issue.getValue(Patient.class).getAddress());
                                patient_age.setText(issue.getValue(Patient.class).getAge());
                                patient_number.setText(issue.getValue(Patient.class).getNumber());
                                paa_id=issue.getValue(Patient.class).getPa_id();
                                checkMedicalRecord(id,paa_id);
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

                nomeConsulta.add("Select Medicine");
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
                                medicine_available_quentity.setText(String.valueOf(quantity));
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