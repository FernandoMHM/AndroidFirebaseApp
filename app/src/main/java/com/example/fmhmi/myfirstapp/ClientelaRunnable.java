//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by FMHMI on 5/19/2018.
 */

public class ClientelaRunnable implements Runnable {

    private final Integer clienteId;

    private FirebaseDatabase firebaseDb;
    private DatabaseReference firebaseDbCursorRef;
    private ValueEventListener listenerCursor;

    private final String CLIENTE = "cliente-";
    private String clienteNode;
    private int clienteIndex = 0;

    private Integer id;
    private String nombreCliente;
    private String emailCliente;
    private String visitingMovie;
    private String visitingDate;

    private ArrayList<Clientela> clientelaArrayList = new ArrayList<Clientela>();


    public ClientelaRunnable(Integer clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public void run() {

        loadArrayClientes(clienteId);
    }

    public ArrayList<Clientela> getClientelaArrayList() {

        return this.clientelaArrayList;
    }

    private void loadArrayClientes(Integer clienteId) {

        firebaseDb = FirebaseDatabase.getInstance();

        clienteIndex = clienteId;
        clienteNode = CLIENTE + clienteIndex;

        if (firebaseDbCursorRef != null) {

            firebaseDbCursorRef.removeEventListener(listenerCursor);
        }

        firebaseDbCursorRef = firebaseDb.getReference(clienteNode);

        listenerCursor = null;

        listenerCursor = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                id = dataSnapshot.child("id").getValue(Integer.class);

                if (id == null) {

                    // Log.d("EHM ->", "ClientelaRunnable - > loadclientelaArrayList() -> onDataChange id == null !!!");

                } else {

                    id = dataSnapshot.child("id").getValue(Integer.class);
                    nombreCliente = dataSnapshot.child("name").getValue(String.class);
                    emailCliente = dataSnapshot.child("email").getValue(String.class);
                    visitingMovie = dataSnapshot.child("movie").getValue(String.class);
                    visitingDate = dataSnapshot.child("date").getValue(String.class);

                    DatabaseArray.arrayListClientes.add(new Clientela(id, nombreCliente, emailCliente, visitingMovie, visitingDate));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("EHM ->", "ClientesRunnable - > Database Error: ", error.toException());
            }
        };

        firebaseDbCursorRef.addValueEventListener(listenerCursor);
    }
}
