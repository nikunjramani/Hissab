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
    static Context ctx;
   static String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    static SharedPreferences sharedPreferences;

    public staticValue(Context ctx) {
        staticValue.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);;
    }

    public static void setPid(int pid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pid", pid);
        editor.apply();
//        setStaticValue();
    }
    public static String getPid(){
        return sharedPreferences.getString("pid", "0");
    }

    public static void setPa_id(int pa_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pa_id", String.valueOf(pa_id));
        editor.apply();
//        setStaticValue();
    }
    public static String getPa_id(){
        return sharedPreferences.getString("pa_id", "0");
    }
    public static void setMid(int mid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mid", String.valueOf(mid));
        editor.apply();
//        setStaticValue();
    }
    public static String  getMid(){
        return sharedPreferences.getString("mid","0");
    }

    public static void getDefaultValue(String user){
        FirebaseDatabase.getInstance().getReference().child("StaticValue").child(String.valueOf(user)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Toast.makeText(ctx, dataSnapshot+"", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        editor.putString("pa_id","1");
                        editor.putString("mid","1");
                        editor.putString("pid","1");
                    }
                editor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
//    public static void setStaticValue(){
//        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference();
//        Map<String, Object> map = new HashMap<>();
//        map.put("StaticValue/"+uid,new Value(getPa_id(),getMid(),getPid()));
//        updateData.updateChildren(map);
//    }
}

class Value{
    String pid,pa_id,mid;

    public Value(String mid, String pa_id, String pid) {
        this.pid = pid;
        this.pa_id = pa_id;
        this.mid = mid;
    }

    public String getPid1() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPa_id1() {
        return pa_id;
    }

    public void setPa_id(String pa_id) {
        this.pa_id = pa_id;
    }

    public String getMid1() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}