package com.example.weatherproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static String firstString;
    static String secString;
    String key;
    private JSONArray arr = new JSONArray();

    String latitude = "";
    String longitude = "";
    String lati = "";
    String longi = "";
    String town;
    String zipC = "";
    boolean useCelsius = false; // Fahrenheit by default

    EditText zipCode;
    Button run, toggleUnit;
    TextView lat, longit, city, weath, weath2, weath3, weath4, date;
    ImageView imag, imag2, imag3, imag4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zipCode = findViewById(R.id.editTextNumberPassword);
        run = findViewById(R.id.location);
        toggleUnit = findViewById(R.id.toggleUnit);
        lat = findViewById(R.id.Latitude);
        longit = findViewById(R.id.Longitude);
        city = findViewById(R.id.City);
        weath = findViewById(R.id.weath);
        weath2 = findViewById(R.id.weath2);
        weath3 = findViewById(R.id.weath3);
        weath4 = findViewById(R.id.weath4);
        imag = findViewById(R.id.imag1);
        imag2 = findViewById(R.id.imag2);
        imag3 = findViewById(R.id.imag3);
        imag4 = findViewById(R.id.imag4);
        date = findViewById(R.id.date);

        // Display current date and time
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/y hh:mm a");
        date.setText(dateForm.format(thisDate));

        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zipC = charSequence.toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        run.setOnClickListener(view -> {
            AsyncTaskDownloadClassThing asyncTask = new AsyncTaskDownloadClassThing();
            asyncTask.execute();

            if (!(latitude.equals("") && longitude.equals(""))) {
                AsyncTaskDownloadClassThing2 asyncTask2 = new AsyncTaskDownloadClassThing2();
                asyncTask2.execute();
            }
        });

        toggleUnit.setOnClickListener(view -> {
            useCelsius = !useCelsius;
            if (!(latitude.equals("") && longitude.equals(""))) {
                AsyncTaskDownloadClassThing2 asyncTask2 = new AsyncTaskDownloadClassThing2();
                asyncTask2.execute();
            }
        });
    }

    public class AsyncTaskDownloadClassThing extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            key = String.valueOf(zipCode.getEditableText());
            try {
                URL weatherKey = new URL("https://api.openweathermap.org/geo/1.0/zip?zip=" + zipC + ",us&appid=dfb78e5904fad1458c4311b74909d54f");
                URLConnection keyConn = weatherKey.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(keyConn.getInputStream()));
                String completeString = "", data = reader.readLine();

                while (data != null) {
                    completeString += data;
                    data = reader.readLine();
                }
                firstString = completeString;

                JSONObject url = new JSONObject(completeString);
                latitude = "Latitude: " + url.getString("lat");
                lati = url.getString("lat");
                longitude = "Longitude: " + url.getString("lon");
                longi = url.getString("lon");
                town = "City: " + url.getString("name");

                runOnUiThread(() -> {
                    lat.setText(latitude);
                    longit.setText(longitude);
                    city.setText(town);
                });
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Invalid ZIP code!", Toast.LENGTH_SHORT).show());
            }
            return null;
        }
    }

    public class AsyncTaskDownloadClassThing2 extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String units = useCelsius ? "metric" : "imperial";
                URL secondWeatherKey = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lati + "&lon=" + longi + "&exclude=daily,minutely,alerts,current&units=" + units + "&appid=dfb78e5904fad1458c4311b74909d54f");
                URLConnection fin = secondWeatherKey.openConnection();
                BufferedReader read = new BufferedReader(new InputStreamReader(fin.getInputStream()));
                String weather = "", data2 = read.readLine();

                while (data2 != null) {
                    weather += data2;
                    data2 = read.readLine();
                }
                secString = weather;

                JSONObject currentWeather = new JSONObject(secString);
                JSONArray array = new JSONArray(currentWeather.getString("hourly"));
                arr = new JSONArray();

                for (int i = 0; i < array.length() && i < 12; i++) { // only show first 4 hours
                    SimpleDateFormat date = new SimpleDateFormat("hh:mm a");
                    java.util.Date time = new java.util.Date((long) Integer.parseInt(array.getJSONObject(i).getString("dt")) * 1000);
                    String ti = date.format(time);

                    double temp = array.getJSONObject(i).getDouble("temp");
                    arr.put(ti);
                    arr.put(String.format("%.1f", temp));
                    JSONArray weatherW = array.getJSONObject(i).getJSONArray("weather");
                    arr.put(weatherW.getJSONObject(0).getString("description"));
                }

                runOnUiThread(() -> {
                    weath.setText("Time: " + arr.get(0) + " | Temp: " + arr.get(1) + "°" + (useCelsius ? "C" : "F") + " | " + arr.get(2));
                    weath2.setText("Time: " + arr.get(3) + " | Temp: " + arr.get(4) + "°" + (useCelsius ? "C" : "F") + " | " + arr.get(5));
                    weath3.setText("Time: " + arr.get(6) + " | Temp: " + arr.get(7) + "°" + (useCelsius ? "C" : "F") + " | " + arr.get(8));
                    weath4.setText("Time: " + arr.get(9) + " | Temp: " + arr.get(10) + "°" + (useCelsius ? "C" : "F") + " | " + arr.get(11));

                    // Set icons
                    setWeatherIcon(arr.get(2).toString(), imag);
                    setWeatherIcon(arr.get(5).toString(), imag2);
                    setWeatherIcon(arr.get(8).toString(), imag3);
                    setWeatherIcon(arr.get(11).toString(), imag4);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void setWeatherIcon(String description, ImageView imageView) {
        description = description.toLowerCase();
        if (description.contains("snow")) {
            imageView.setImageResource(R.drawable.snow);
        } else if (description.contains("rain")) {
            imageView.setImageResource(R.drawable.rain);
        } else if (description.contains("cloud")) {
            imageView.setImageResource(R.drawable.cloud);
        } else if (description.contains("sun") || description.contains("clear")) {
            imageView.setImageResource(R.drawable.sky);
        }
    }
}
