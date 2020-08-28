package com.hissab;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.patient.Patient;
import com.hissab.HomePage.ui.product.Product;

import java.util.HashMap;
import java.util.Map;

public class staticValue {
    Context ctx;
   static String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    private static SharedPreferences sharedPreferences;

    public staticValue(Context ctx) {
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);;
    }

    public static void setPid(int pid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pid", String.valueOf(pid));
        editor.apply();
        setStaticValue();
    }
    public static String getPid(){
        return sharedPreferences.getString("pid", "0");
    }

    public static void setPa_id(int pa_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pa_id", String.valueOf(pa_id));
        editor.apply();
        setStaticValue();
    }
    public static String getPa_id(){
        return sharedPreferences.getString("pa_id", "0");
    }
    public static void setMid(int mid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mid", String.valueOf(mid));
        editor.apply();
        setStaticValue();
    }
    public static String  getMid(){
        return sharedPreferences.getString("mid","0");
    }

    public static void getDefaultValue(String user){
        FirebaseDatabase.getInstance().getReference().child("StaticValue").child(String.valueOf(user)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                UserValue userValue=dataSnapshot.getValue(UserValue.class);
                editor.putString("pa_id", userValue.getPa_id());
                editor.putString("mid",userValue.getMid());
                editor.putString("pid",userValue.getPid());
                editor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void setStaticValue(){
        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> map = new HashMap<>();
        map.put("StaticValue/"+uid,new UserValue(getMid(),getPa_id(),getPid()));
        updateData.updateChildren(map);
    }
}

class UserValue{
    String mid,pa_id,pid;

    public UserValue(){}
    public UserValue(String mid, String pa_id, String pid) {
        this.mid = mid;
        this.pa_id = pa_id;
        this.pid = pid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPa_id() {
        return pa_id;
    }

    public void setPa_id(String pa_id) {
        this.pa_id = pa_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}