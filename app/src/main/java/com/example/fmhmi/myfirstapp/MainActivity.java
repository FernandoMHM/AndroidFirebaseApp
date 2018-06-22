//Fernando M. Hernández Millet
//Num.Est.Y00418050
//COMP2850-Lab10
//05/17/2018

package com.example.fmhmi.myfirstapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {

    /*private CheckBox isHandycapped;
    private Spinner  ticketQty;*/

    private EditText editTextClienteId;
    private EditText editTextClienteName;
    private EditText editTextClienteEmail;
    private EditText editTextVisitingMovie;
    private EditText editTextVisitingDate;

    private Button btnMovieList;
    private ImageButton searchImageBtn;
    private ImageButton calendarImageButton;

    private Integer id;
    private String nombreCliente;
    private String emailCliente;
    private String visitingMovie;
    private String visitingDate;

    private int dia;
    private int mes;
    private int year;

    private FirebaseDatabase firebaseDb;
    private DatabaseReference firebaseDbReference;
    private ValueEventListener listener;

    private ClientelaRunnable clientelaRunnable;
    private final String CLIENTE = "cliente-";
    private static int THREAD_TIME  = 100;
    private static final int DATE_DIALOG_ID = 999;
    private Handler handler;

    private String clienteNode;
    private int clienteIndex = 0;
    private Integer clienteId;
    private Clientela clientes;


    private Boolean isRecordQueried = false;

    private ColorFilter filter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextClienteId = (EditText) findViewById(R.id.editText_Id);
        editTextClienteName = (EditText) findViewById(R.id.editText_Name);
        editTextClienteEmail = (EditText) findViewById(R.id.editText_Email);
        editTextVisitingMovie = (EditText) findViewById(R.id.editText_Movie);
        editTextVisitingDate = (EditText) findViewById(R.id.editText_Visit);

        calendarImageButton = (ImageButton) findViewById(R.id.calendar_imgBtn);
        searchImageBtn = (ImageButton) findViewById(R.id.search_imgBtn);
        btnMovieList = (Button) findViewById(R.id.chooseMovie_btn);

        setFirebaseFields();

        btnMovieList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View arg0){
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);

                startActivityForResult(intent, 2);
            }
        } );

        editTextVisitingMovie.setEnabled(false);
        editTextVisitingDate.setEnabled(false);
        calendarImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }

    private void setFirebaseFields() {

        clienteNode = CLIENTE + clienteIndex;

        try {

            DatabaseArray.isGetFirebaseRecord = getFirebaseRecords(clienteNode);

            loadArrayClientes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean getFirebaseRecords(String clienteNode) {

        DatabaseArray.isGetFirebaseRecord = false;

        firebaseDb = FirebaseDatabase.getInstance();
        firebaseDbReference = firebaseDb.getReference(clienteNode);

        listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                id = dataSnapshot.child("id").getValue(Integer.class);

                if (id == null) {

                    setClearFields();

                    DatabaseArray.isGetFirebaseRecord = false;

                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.error_record_db),
                            Toast.LENGTH_LONG).show();
                } else {

                    editTextClienteId.setText(id.toString());

                    nombreCliente = dataSnapshot.child("name").getValue(String.class);
                    editTextClienteName.setText(nombreCliente);

                    emailCliente = dataSnapshot.child("email").getValue(String.class);
                    editTextClienteEmail.setText(emailCliente);

                    visitingMovie = dataSnapshot.child("movie").getValue(String.class);
                    editTextVisitingMovie.setText(visitingMovie);

                    visitingDate = dataSnapshot.child("date").getValue(String.class);
                    editTextVisitingDate.setText(visitingDate);

                    DatabaseArray.isGetFirebaseRecord = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.error_db_message),
                        Toast.LENGTH_LONG).show();
            }

        };

        firebaseDbReference.addValueEventListener(listener);

        return DatabaseArray.isGetFirebaseRecord;
    }

    //Boton de Insertar nueva informacion/Clear
    public void insertNewRecord(View view) {

        setClearFields();
    }

    //Boton de Borrar Record de Firebase
    public void deleteRecord(View view) {

        clienteIndex = Integer.parseInt(editTextClienteId.getText().toString());

        clienteNode = CLIENTE + clienteIndex;

        firebaseDb.getReference(clienteNode).removeValue();

        loadArrayClientes();

        Toast.makeText(this,
                getResources().getString(R.string.msg_delete_db),
                Toast.LENGTH_LONG).show();

        firebaseDbReference.removeEventListener(listener);

        clienteIndex = 1;
        clienteNode = CLIENTE + clienteIndex;

        getFirebaseRecords(clienteNode);
    }

    //Boton de Actualizar Record en Firebase
    public void updateAddRecord(View view) {

        if (TextUtils.isEmpty(editTextClienteId.getText().toString())) {
            editTextClienteId.setError(getResources().getString(R.string.error_field_message));
            return;
        }

        if (TextUtils.isEmpty(editTextClienteName.getText().toString())) {
            editTextClienteName.setError(getResources().getString(R.string.error_field_message));
            return;
        }

        if (TextUtils.isEmpty(editTextClienteEmail.getText().toString())) {
            editTextClienteEmail.setError(getResources().getString(R.string.error_field_message));
            return;
        }

        if (TextUtils.isEmpty(editTextVisitingMovie.getText().toString())) {
            editTextVisitingMovie.setError(getResources().getString(R.string.error_field_message));
            return;
        }

        if (TextUtils.isEmpty(editTextVisitingDate.getText().toString())) {
            editTextVisitingDate.setError(getResources().getString(R.string.error_field_message));
            return;
        }

        clienteIndex = Integer.parseInt(editTextClienteId.getText().toString());

        clienteNode = CLIENTE + clienteIndex;

        clientes = new Clientela(
                clienteIndex,
                editTextClienteName.getText().toString(),
                editTextClienteEmail.getText().toString(),
                editTextVisitingMovie.getText().toString(),
                editTextVisitingDate.getText().toString());

        firebaseDb.getReference(clienteNode).setValue(clientes);

        loadArrayClientes();

        Toast.makeText(this,
                getResources().getString(R.string.msg_update_db),
                Toast.LENGTH_LONG).show();
    }

    //Boton para ir al record anterior
    public void prevoiusRecord(View view) {

        clienteId = Integer.parseInt(editTextClienteId.getText().toString());

        if (DatabaseArray.clienteIndexStart.intValue() == 0) {

            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.msg_firstRec_db),
                    Toast.LENGTH_SHORT).show();

            return;
        }

        for (int i = DatabaseArray.clienteIndexStart; i <= DatabaseArray.arrayListClientes.size() - 1; i--) {

            Integer iteratorClienteId = DatabaseArray.arrayListClientes.get(i).getId();
            String iteratorClienteName = DatabaseArray.arrayListClientes.get(i).getName();
            String iteratorClienteEmail = DatabaseArray.arrayListClientes.get(i).getEmail();
            String iteratorVisitingMovie = DatabaseArray.arrayListClientes.get(i).getMovie();
            String iteratorVisitingDate = DatabaseArray.arrayListClientes.get(i).getDate();

            if (iteratorClienteId.intValue() != clienteId.intValue()) {

                try {

                    editTextClienteId.setText(iteratorClienteId.toString());
                    editTextClienteName.setText(iteratorClienteName);
                    editTextClienteEmail.setText(iteratorClienteEmail);
                    editTextVisitingMovie.setText(iteratorVisitingMovie);
                    editTextVisitingDate.setText(iteratorVisitingDate);

                    clienteId = Integer.parseInt(editTextClienteId.getText().toString());
                    DatabaseArray.clienteIndexStart = i;

                    break;

                } catch (NoSuchElementException e) {
                    Log.d("EHM ->", "prevoiusRecord() -> clientesIterator.next() -> NoSuchElementException Error.");

                    break;
                }
            }
        }
    }

    //Boton para ir al proximo record
    public void nextRecord(View view) {

        clienteId = Integer.parseInt(editTextClienteId.getText().toString());

        if (DatabaseArray.clienteIndexStart.intValue() == (DatabaseArray.arrayListClientes.size() - 1)) {

            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.msg_lastRec_db),
                    Toast.LENGTH_SHORT).show();

            return;
        }

        for (int i = DatabaseArray.clienteIndexStart; i < DatabaseArray.arrayListClientes.size(); i++) {
            Integer iteratorClienteId = DatabaseArray.arrayListClientes.get(i).getId();
            String iteratorClienteName = DatabaseArray.arrayListClientes.get(i).getName();
            String iteratorClienteEmail = DatabaseArray.arrayListClientes.get(i).getEmail();
            String iteratorVisitingMovie = DatabaseArray.arrayListClientes.get(i).getMovie();
            String iteratorVisitingDate = DatabaseArray.arrayListClientes.get(i).getDate();

            if (iteratorClienteId.intValue() == clienteId.intValue()) {

                DatabaseArray.clienteIndexStart = 0;

            } else {

                try {

                    editTextClienteId.setText(iteratorClienteId.toString());
                    editTextClienteName.setText(iteratorClienteName);
                    editTextClienteEmail.setText(iteratorClienteEmail);
                    editTextVisitingMovie.setText(iteratorVisitingMovie);
                    editTextVisitingDate.setText(iteratorVisitingDate);

                    clienteId = Integer.parseInt(editTextClienteId.getText().toString());
                    DatabaseArray.clienteIndexStart = i;

                    break;

                } catch (NoSuchElementException e) {
                    Log.d("EHM ->", "nextRecord() -> clientesIterator.next() ->  NoSuchElementException Error.");

                    break;
                }
            }
        }
    }

    //Boton de Seleccionar un Record de Firebase
    public void searchRecord(View view) {

        DatabaseArray.isGetFirebaseRecord = false;

        if (!isRecordQueried) {

            isRecordQueried = true;

            filter = searchImageBtn.getColorFilter();

            searchImageBtn.setColorFilter(ContextCompat.getColor(this, R.color.red),
                    android.graphics.PorterDuff.Mode.SRC_IN);

            searchImageBtn.setBackgroundTintList(ContextCompat.getColorStateList(this,
                    R.color.yellow));

            setClearFields();

        } else {

            if (!editTextClienteId.getText().toString().isEmpty()) {

                clienteIndex = Integer.parseInt(editTextClienteId.getText().toString());

                clienteNode = CLIENTE + clienteIndex;

                firebaseDbReference.removeEventListener(listener);

                QueryClientes ClienteQueryThread = new QueryClientes(clienteNode);
                new Thread(ClienteQueryThread).start();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        if (!DatabaseArray.isGetFirebaseRecord) {

                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.error_record_db),
                                    Toast.LENGTH_LONG).show();

                        } else {

                            searchImageBtn.setColorFilter(filter);

                            searchImageBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                                    R.color.gray));

                            isRecordQueried = false;
                            DatabaseArray.isGetFirebaseRecord = false;
                        }

                    }
                }, THREAD_TIME);

            } else {

                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.error_record_db),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setClearFields() {

        id = 0;
        nombreCliente = "";
        emailCliente = "";
        visitingMovie = "";
        visitingDate = "";

        editTextClienteId.setText("");
        editTextClienteName.setText("");
        editTextClienteEmail.setText("");
        editTextVisitingMovie.setText("");
        editTextVisitingDate.setText("");
    }

    private void loadArrayClientes() {

        DatabaseArray.arrayListClientes.removeAll(DatabaseArray.arrayListClientes);
        DatabaseArray.arrayListClientes.clear();

        handler = new Handler();

        for(int idNumber = 0; idNumber < 100; idNumber++) {

            clientelaRunnable = new ClientelaRunnable(idNumber);
            handler.postDelayed(clientelaRunnable, THREAD_TIME);
        }
    }

    private class ContactoQueryRecordAsyncTask extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... clienteNode) {

            int count = clienteNode.length;

            for (int i = 0; i < count; i++) {

                DatabaseArray.isGetFirebaseRecord = getFirebaseRecords(clienteNode[i]);

                if (isCancelled()) {

                    break;
                }
            }

            return DatabaseArray.isGetFirebaseRecord;
        }

        @Override
        protected void onPostExecute(Boolean isFirebaseRecordFound) {

            if (!DatabaseArray.isGetFirebaseRecord) {

                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.error_record_db),
                        Toast.LENGTH_LONG).show();

            } else {

                searchImageBtn.setColorFilter(filter);

                searchImageBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.gray));

                isRecordQueried = false;
                DatabaseArray.isGetFirebaseRecord = false;
            }
        }
    }

    class QueryClientes implements Runnable {

        String clienteNode;

        QueryClientes(String clienteNode) {

            this.clienteNode = clienteNode;
        }

        public void run() {

            DatabaseArray.isGetFirebaseRecord = getFirebaseRecords(this.clienteNode);
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:

                //Current Date
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR) - 18;
                mes = calendar.get(Calendar.MONTH);
                dia = calendar.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(this, datePickerListener,
                        year, mes,dia);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int yearSeleccionado,
                              int mesSeleccionado, int diaSeleccionado) {
            dia = diaSeleccionado;
            mes = mesSeleccionado;
            year = yearSeleccionado;

            editTextVisitingDate.setText(diaSeleccionado + " / " + (mesSeleccionado + 1) + " / "
                    + yearSeleccionado);

            editTextClienteId.requestFocus();
        }
    };

    //Options Menu Create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fields, menu);
        return true;
    }
    //Options Menu Fields
    @Override
    public boolean onOptionsItemSelected(MenuItem fields) {

        int id = fields.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText( this, "Settings was selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_about) {

            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);

        }

        if (id == R.id.action_print) {
            Toast.makeText( this, "Print was selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(fields);
    }
    //Activity for Result Movies
    @Override
    protected void onActivityResult(int codeRequest, int codeResult, Intent dataList) {
        super.onActivityResult(codeRequest, codeResult, dataList);

        String moviePicked = "";

        if(codeRequest == 2)
        {

            //RESULT_CANCELED = 0
            //RESULT_OK = -1

            if(codeResult == RESULT_OK)  {

                if(dataList != null) {
                    moviePicked = dataList.getStringExtra("MESSAGE");
                }

                editTextVisitingMovie.setText(moviePicked);
            }
        }
    }
}
