package com.hissab.HomePage.ui.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hissab.Login.Login;
import com.hissab.R;

public class Account extends Fragment {
    ImageView imageView;
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_account, container, false);
        mAuth = FirebaseAuth.getInstance();

        imageView = root.findViewById(R.id.imageView);
        textName = root.findViewById(R.id.textViewName);
        textEmail = root.findViewById(R.id.textViewEmail);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account");

        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);

        textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), Login.class));
        }
    }
}