package com.example.adelson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        JSONObject jsonObj = new JSONObject(json);
//        JSONArray array = jsonObj.getJSONArray("results");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date data;
//
//        JSONObject objArray = array.getJSONObject(0);
//
//        JSONObject obj = objArray.getJSONObject("user");
////Atribui os objetos que estão nas camadas mais altas
//        pessoa.setEmail(obj.getString("email"));
//        pessoa.setUsername(obj.getString("username"));
//        pessoa.setSenha(obj.getString("password"));
//        pessoa.setTelefone(obj.getString("phone"));
//        data = new Date(obj.getLong("dob")*1000);
//        pessoa.setNascimento(sdf.format(data));

    }

    public void estado(View view) {
        startActivity(new Intent(this, EstadoActivity.class));
    }

    public void localizacao(View view) {
        startActivity(new Intent(this, LocalizacaoActivity.class));
    }
}