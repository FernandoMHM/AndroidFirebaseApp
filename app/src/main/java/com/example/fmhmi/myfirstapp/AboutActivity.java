//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by FMHMI on 5/19/2018.
 */

public class AboutActivity extends AppCompatActivity {

    private TextView aboutInfo;

    private String nombreEstudiante = "Fernando M. Hernández Millet";
    private String nombreAplicacion = "Island Cinemas Application";
    private String version = "1.0.1";
    private String dateCreated = "05/16/2018";
    private String lastUpdateDate = "05/20/2018";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutInfo = (TextView) findViewById(R.id.textView_about);
        aboutInfo.setText("Por: " + nombreEstudiante + "\n");
        aboutInfo.append("Aplicación: " + nombreAplicacion + "\n");
        aboutInfo.append("Versión: " + version + "\n");
        aboutInfo.append("Creado en: " + dateCreated + "\n");
        aboutInfo.append("Ultima Actualización: " + lastUpdateDate + "\n");

    }

    public void returnHomefromAbout (View view) {

        Intent i = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(i);

        Toast.makeText( this, "Home Logo Clicked...", Toast.LENGTH_SHORT).show();
    }
}
