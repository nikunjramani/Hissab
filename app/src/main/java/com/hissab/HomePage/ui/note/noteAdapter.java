package com.hissab.HomePage.ui.note;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hissab.HomePage.ui.product.Product;
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

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {

    Context ctx;
    List<Note> noteList=new ArrayList<>();

    public noteAdapter(Context ctx, List<Note> noteList) {
        this.ctx = ctx;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public noteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.note_list,parent,false);
        return new noteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noteAdapter.ViewHolder holder, int position) {
        final Note note=noteList.get(position);
        holder.name.setText(note.getNoteName());
        holder.description.setText(note.getNoteDescription());
        holder.edit_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText note_name,note_description;
                View view = View.inflate(ctx, R.layout.add_note, null);
                note_name = view.findViewById(R.id.note_name);
                final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                note_description = view.findViewById(R.id.note_description);
                note_name.setText(note.getNoteName());
                note_description.setText(note.getNoteDescription());
                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                alert.setView(view);
                alert.setTitle("Add Note");
                alert.setPositiveButton("Add Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        Map<String, Object> map = new HashMap<>();
                        map.put("Note/"+uid+"/"+note.getNid(),new Note(staticValue.getNid(),note_name.getText().toString(),note_description.getText().toString()));
                        FirebaseDatabase.getInstance().getReference().updateChildren(map);
                        AppCompatActivity activity = (AppCompatActivity) ctx;
                        Fragment myFragment = new note();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,description,edit_note;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.note_name);
            description=itemView.findViewById(R.id.note_description);
            edit_note=itemView.findViewById(R.id.edit_note);
        }
    }
}
