package com.hunter.crudfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hunter.crudfirebase.adapter.PetAdapter;
import com.hunter.crudfirebase.model.Pet;

public class MainActivity extends AppCompatActivity {

    private Button btnAgregarMascota;
    private RecyclerView rcvLista;
    PetAdapter adapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore= FirebaseFirestore.getInstance();

        btnAgregarMascota = findViewById(R.id.btnAgregarMascota);
        rcvLista = findViewById(R.id.rcvLista);
        rcvLista.setLayoutManager(new LinearLayoutManager(this));

        //Query
        Query query = mFirestore.collection("pet");

        FirestoreRecyclerOptions<Pet>firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Pet>().setQuery(query, Pet.class).build();
        adapter = new PetAdapter(firestoreRecyclerOptions, this);

        adapter.notifyDataSetChanged();
        rcvLista.setAdapter(adapter);

        btnAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrarMascota.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}