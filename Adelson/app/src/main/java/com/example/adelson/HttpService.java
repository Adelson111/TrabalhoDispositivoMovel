package com.example.adelson;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, EstadoModel> {

    URL url;

    public HttpService(String rota) {
        try {
            url = new URL("https://covid19-brazil-api.now.sh/api/report/v1"+rota);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected EstadoModel doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Gson().fromJson(resposta.toString(), EstadoModel.class);
    }
}
