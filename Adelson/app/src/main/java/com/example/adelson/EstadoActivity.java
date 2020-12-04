package com.example.adelson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EstadoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String siglaEstado;
    TextView estado;
    TextView casos;
    TextView mortos;
    TextView suspeitos;
    TextView recuperados;
    TextView data_hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);


        estado = findViewById(R.id.textView_estado);
        casos = findViewById(R.id.textView_casos);
        mortos = findViewById(R.id.textView_mortos);
        suspeitos = findViewById(R.id.textView_suspeitos);
        recuperados = findViewById(R.id.textView_recuperados);
        data_hora = findViewById(R.id.textView_data_hora);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pegarEstado(View view) {
        try {
            EstadoModel retorno = new HttpService("/brazil/uf/"+siglaEstado.toLowerCase()).execute().get();
            estado.setText(retorno.getState());
            casos.setText(retorno.getCases());
            mortos.setText(retorno.getDeaths());
            suspeitos.setText(retorno.getSuspects());
            recuperados.setText(retorno.getRefuses());
            data_hora.setText(new SimpleDateFormat("dd/MM/yyy HH:mm").format(retorno.getDatetime()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        siglaEstado = String.valueOf(parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}