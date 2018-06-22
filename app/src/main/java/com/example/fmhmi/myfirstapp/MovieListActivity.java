//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by FMHMI on 5/19/2018.
 */

public class MovieListActivity extends ListActivity {

    static final String[] LIST_OF_MOVIES = new String[] { "1950", "A Quite Place",
            "Avengers: Infinity War", "Beirut", "Breaking In", "Deadpool 2", "El Silencio del Viento",
            "I Can Only Imagine", "I Feel Pretty", "Isle of Dogs", "La Sagrada Familia", "La Tribu",
            "Life of the Party", "Nothing to Loose"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_movie, LIST_OF_MOVIES));

        ListView movieList = getListView();
        movieList.setTextFilterEnabled(true);

        movieList.setDivider(getResources().getDrawable(R.drawable.divider));
        movieList.setDividerHeight(10);

        View movieHeader = getLayoutInflater().inflate(R.layout.activity_movie_heather, null);
        movieList.addHeaderView(movieHeader);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position>0) {
                    Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                    //TODO Send data to MainActivity
                    String moviePicked = parent.getItemAtPosition(position).toString();

                    Intent intent = new Intent();
                    intent.putExtra("MESSAGE", moviePicked);

                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }

    public void returnHomeFromListView (View view) {

        Intent i = new Intent(MovieListActivity.this, MainActivity.class);
        startActivity(i);

        Toast.makeText( this, "Home Logo Clicked...", Toast.LENGTH_SHORT).show();
    }


}
