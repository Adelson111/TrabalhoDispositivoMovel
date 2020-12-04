package com.example.adelson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LocalizacaoActivity extends AppCompatActivity {

    private Address endereco;
    Map<String, String> estados = new HashMap<>();
    boolean carregado = false;

    String sigla = "";

    TextView carregamento;
    TextView falha;
    TextView estado;
    TextView casos;
    TextView mortos;
    TextView suspeitos;
    TextView recuperados;
    TextView data_hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        estado = findViewById(R.id.textView_estado);
        casos = findViewById(R.id.textView_casos);
        mortos = findViewById(R.id.textView_mortos);
        suspeitos = findViewById(R.id.textView_suspeitos);
        recuperados = findViewById(R.id.textView_recuperados);
        data_hora = findViewById(R.id.textView_data_hora);

        carregamento = findViewById(R.id.textView_carregamento);
        falha = findViewById(R.id.textView_falha);
        pedirPermissoes();

        estados.put("AC", "Acre");
        estados.put("AL", "Alagoas");
        estados.put("AP", "Amapá");
        estados.put("AM", "Amazonas");
        estados.put("BA", "Bahia");
        estados.put("CE", "Ceará");
        estados.put("ES", "Espírito Santo");
        estados.put("GO", "Goiás");
        estados.put("MA", "Maranhão");
        estados.put("MT", " Mato Grosso");
        estados.put("MS", " Mato Grosso do Sul");
        estados.put("MG", "Minas Gerais");
        estados.put("PA", "Pará");
        estados.put("PB", "Paraíba");
        estados.put("PR", "Paraná");
        estados.put("PE", "Pernambuco");
        estados.put("PI", "Piauí");
        estados.put("RJ", "Rio de Janeiro");
        estados.put("RN", "Rio Grande do Norte");
        estados.put("RS", "Rio Grande do Sul");
        estados.put("RO", "Rondônia");
        estados.put("RR", "Roraima");
        estados.put("SC", "Santa Catarina");
        estados.put("SP", "São Paulo");
        estados.put("SE", "Sergipe");
        estados.put("TO", "Tocantins");
        estados.put("DF", "Distrito Federal");

    }

    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            configurarServico();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(this, "Permissão não aceita!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void configurarServico(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onLocationChanged(Location location) {
                    if(carregado) {
                        carregamento.setText("Carregado");
                    } else {
                        try {
                            endereco = buscarEndereco(location.getLatitude(), location.getLongitude());
                            estados.forEach((k,v) -> {
                                if(endereco.getAdminArea().equals("State of "+v)) {
                                    sigla = k;
                                }
                            });
                            if(sigla != "") {
                                EstadoModel retorno = new HttpService("/brazil/uf/"+sigla.toLowerCase()).execute().get();
                                estado.setText(retorno.getState());
                                casos.setText(retorno.getCases());
                                mortos.setText(retorno.getDeaths());
                                suspeitos.setText(retorno.getSuspects());
                                recuperados.setText(retorno.getRefuses());
                                data_hora.setText(new SimpleDateFormat("dd/MM/yyy HH:mm").format(retorno.getDatetime()));
                                carregado = true;
                            } else {
                                carregado = true;
                                falha.setText("Você não está no Brasil!!!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Address buscarEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if(addresses.size() > 0) {
            address = addresses.get(0);
        }
        return address;
    }

}