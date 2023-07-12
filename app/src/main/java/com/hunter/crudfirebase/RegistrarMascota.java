package com.hunter.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;


public class RegistrarMascota extends AppCompatActivity {

    private EditText etNombreMascota;
    private EditText etEdadMascota;
    private EditText etColorMascota;
    private Button btnAgregarRegistroMascota;

    //Conexion a Firebase
    private FirebaseFirestore mfirestore;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        this.setTitle("Registrar nueva mascota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mfirestore = FirebaseFirestore.getInstance();

        String id = getIntent().getStringExtra("id_pet");



        //Configuracion para registro sin conexion
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        mfirestore.setFirestoreSettings(settings);

        etNombreMascota = findViewById(R.id.etNombreMascota);
        etEdadMascota = findViewById(R.id.etEdadMascota);
        etColorMascota = findViewById(R.id.etColorMascota);
        btnAgregarRegistroMascota = findViewById(R.id.btnAgregarRegistroMascota);



        if(id == null || id == ""){
            btnAgregarRegistroMascota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String namePet = etNombreMascota.getText().toString().trim();
                    String agePet = etEdadMascota.getText().toString().trim();
                    String colorPet = etColorMascota.getText().toString().trim();

                    if(namePet.isEmpty() && agePet.isEmpty() && colorPet.isEmpty()){
                        Toast.makeText(RegistrarMascota.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postPet(namePet, agePet, colorPet);
                    }


                }
            });
        }else{
            btnAgregarRegistroMascota.setText("ACTUALIZAR DATOS MASCOTA");
            getPet(id);
            btnAgregarRegistroMascota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namePet = etNombreMascota.getText().toString().trim();
                    String agePet = etEdadMascota.getText().toString().trim();
                    String colorPet = etColorMascota.getText().toString().trim();

                    if(namePet.isEmpty() && agePet.isEmpty() && colorPet.isEmpty()){
                        Toast.makeText(RegistrarMascota.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updatePet(namePet, agePet, colorPet, id);
                    }
                }
            });
        }

    }

    private void updatePet(String namePet, String agePet, String colorPet, String id) {

        Map<String, Object> map = new HashMap<>() ;
        map.put("name", namePet);
        map.put("age", agePet);
        map.put("colorPet", colorPet);

        mfirestore.collection("pet").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegistrarMascota.this, "Actualizado exitosamente \nID: " +  id , Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrarMascota.this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postPet(String namePet, String agePet, String colorPet) {

        //Creación de Map
        Map<String, Object> map = new HashMap<>() ;
        map.put("name", namePet);
        map.put("age", agePet);
        map.put("colorPet", colorPet);

        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(RegistrarMascota.this, "Creado exitosamente \nID: " + documentReference.getId() , Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrarMascota.this, "Error al registrar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getPet(String id){
        mfirestore.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namePet = documentSnapshot.getString("name");
                String agePet = documentSnapshot.getString("age");
                String colorPet = documentSnapshot.getString("colorPet");

                etNombreMascota.setText(namePet);
                etEdadMascota.setText(agePet);
                etColorMascota.setText(colorPet);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrarMascota.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}