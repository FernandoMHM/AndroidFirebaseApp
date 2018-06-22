//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by FMHMI on 5/18/2018.
 */

public class DatabaseArray extends Application {

        public static ArrayList<Clientela> arrayListClientes = new ArrayList<Clientela>();

        public static Integer clienteIndexStart = 0;

        public static Boolean isGetFirebaseRecord = false;

}

