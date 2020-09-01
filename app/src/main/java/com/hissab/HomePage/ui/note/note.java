package com.hissab.HomePage.ui.note;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hissab.HomePage.ui.product.Product;
import com.hissab.HomePage.ui.product.Stoke;
import com.hissab.HomePage.ui.product.product;
import com.hissab.HomePage.ui.product.productAdapter;
import com.hissab.R;
import com.hissab.staticValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class note extends Fragment {
    private List<Note> noteList = new ArrayList<>();

    Context ctx;
    String uid;
    CardView add_note;
    RecyclerView note_list;
    noteAdapter noteAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_note, container, false);
        ctx=getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Note");
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mDatabase = ;
        add_note=root.findViewById(R.id.add_note);
        note_list=root.findViewById(R.id.note_list);
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText note_name,note_description;
                View view = View.inflate(ctx, R.layout.add_note, null);
                note_name = view.findViewById(R.id.note_name);
                note_description = view.findViewById(R.id.note_description);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setView(view);
                alert.setTitle("Add Note");
                alert.setPositiveButton("Add Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Map<String, Object> map = new HashMap<>();
                        FirebaseDatabase.getInstance().getReference().child("Note").child(uid).child(staticValue.getNid()).setValue(new Note(staticValue.getNid(),note_name.getText().toString(),note_description.getText().toString()));
                        staticValue.setNid(Integer.parseInt(staticValue.getNid())+1);
                        getFragmentManager().beginTransaction().detach(note.this).commit();
                        getFragmentManager().beginTransaction().attach(note.this).commit();
                    }
                });
                alert.show();
            }
        });
        linearLayoutManager = new LinearLayoutManager(ctx);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        note_list.setHasFixedSize(true);
        note_list.setLayoutManager(linearLayoutManager);
        fetch();
        return root;
    }

    private void fetch() {
        note_list.setLayoutManager(new LinearLayoutManager(ctx));
        FirebaseDatabase.getInstance().getReference().child("Note").child(uid).orderByChild(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    Note user = dataSnapshot1.getValue(Note.class);
                    noteList.add(user);
                }
                noteAdapter = new noteAdapter(ctx, noteList);
                note_list.setAdapter(noteAdapter);
                noteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}