//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import java.util.ArrayList;

/**
 * Created by FMHMI on 5/17/2018.
 */

public class Clientela {

    private Integer id;

    private ArrayList<Clientela> clientelaArrayList = new ArrayList<Clientela>();

    public Clientela(Integer id, String nombreCliente, String emailCliente, String visitingMovie, String visitingDate) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.visitingMovie = visitingMovie;
        this.visitingDate = visitingDate;
    }

    private String nombreCliente;
    private String emailCliente;
    private String visitingMovie;
    private String visitingDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return nombreCliente;
    }

    public void setName(String name) {this.nombreCliente = name;}

    public String getEmail() {
        return emailCliente;
    }

    public void setEmail(String email) {
        this.emailCliente= email;
    }

    public String getMovie() {
        return visitingMovie;
    }

    public void setMovie(String movie) {
        this.visitingMovie= movie;
    }

    public String getDate() {
        return visitingDate;
    }

    public void setDate(String date) {
        this.visitingDate= date;
    }

    @Override
    public String toString() {
        return "Clientes{" + "id = " + id + ", name = '" + nombreCliente + '\'' + ", email = '" + emailCliente + '\'' +
                ", movie = '" + visitingMovie + '\'' + ", date = '" + visitingDate + '\'' +'}';
    }
}
