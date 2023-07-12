package com.hunter.crudfirebase.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hunter.crudfirebase.R;
import com.hunter.crudfirebase.RegistrarMascota;
import com.hunter.crudfirebase.model.Pet;

import org.w3c.dom.Text;

public class PetAdapter extends FirestoreRecyclerAdapter<Pet, PetAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    public PetAdapter(@NonNull FirestoreRecyclerOptions<Pet> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Pet model) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.tvNombreMascotaItem.setText(model.getName());
        holder.tvColorMascotaItem.setText(model.getColorPet());
        holder.tvEdadMascotaItem.setText(model.getAge());

        holder.btnEditarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, RegistrarMascota.class);
                i.putExtra("id_pet", id);

                activity.startActivity(i);
            }
        });

        holder.btnEliminarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePet(id);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pet_single, parent, false);
        return new ViewHolder(v);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreMascotaItem, tvEdadMascotaItem, tvColorMascotaItem;
        Button btnEditarItem, btnEliminarItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombreMascotaItem = itemView.findViewById(R.id.tvNombreMascotaItem);
            tvColorMascotaItem = itemView.findViewById(R.id.tvColorMascotaItem);
            tvEdadMascotaItem = itemView.findViewById(R.id.tvEdadMascotaItem);

            btnEditarItem = itemView.findViewById(R.id.btnEditarItem);
            btnEliminarItem = itemView.findViewById(R.id.btnEliminarItem);

        }
    }

    private void deletePet(String id){
        mFirestore.collection("pet").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Registro Eliminado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
