package com.example.minitpopenweather;
import com.example.minitpopenweather.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.CheckBox;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private String[] mesDestinations = {"Paris", "Rome", "Tokyo", "Barcelone", "Istanbul", "Londres", "Bangkok", "Sydney", "Venise", "Prague", "Mexico", "Pékin", "Vancouver"};

    private ActivityMainBinding binding;

    private ArrayList<Forecast> forecastList;
    private ArrayList<Forecast> destinationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forecastList = new ArrayList<>();
        destinationsList = new ArrayList<>();

        for (String destination : mesDestinations) {
            callDestinantion(destination);
        }

        binding.buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.destinations.setText("");
                boolean soleil = binding.checkBoxSoleil.isChecked();
                if (soleil) {
                    for (Forecast monForecast : forecastList) {
                        if(monForecast.getWeather()[0].getMain().equals("Clear")){
                            destinationsList.add(monForecast);
                        }
                    }
                    StringBuilder destinationsString = new StringBuilder();
                    for (Forecast destination : destinationsList) {
                        destinationsString.append("Nom de la destination: ").append(destination.getCity()).append(" ");
                        destinationsString.append("Conditions météorologiques: ").append(destination.getWeather()[0].getMain()).append(" ");
                        // Ajoutez d'autres informations nécessaires
                    }
                    // Attribuez la chaîne au TextView
                    binding.destinations.setText(destinationsString.toString());
                }
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void callDestinantion(String ville) {
        OpenWeatherServices services = RetrofitClientInstance.getRetrofitInstance().create(OpenWeatherServices.class);
        Call<Forecast> call = services.getForcast(ville, "0cf11331d17e1d92df5e18d361645a3a");

        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Forecast forecast = response.body();
                    forecastList.add(forecast);

                } else {
                    Toast.makeText(MainActivity.this, "Réponse non réussie", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Une erreur est survenue : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}