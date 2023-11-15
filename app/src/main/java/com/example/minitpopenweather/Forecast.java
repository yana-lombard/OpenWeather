package com.example.minitpopenweather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

    public class Forecast implements Serializable {
        @SerializedName("dt")
        private int datetime;

        @SerializedName("name")
        private String city;

        @SerializedName("weather")
        private Weather[] weather;

        public Weather[] getWeather() {
            return weather;
        }

        public String getCity() {
            return city;
        }
    }


