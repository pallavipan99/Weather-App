package com.example.weatherproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    static String firstString;
    static String secString;
    String key;
    private JSONArray arr = new JSONArray();

    String time1;
    String time2;
    String time3;
    String time4;

    String weatherDisc;
    String weatherDisc2;
    String weatherDisc3;
    String weatherDisc4;
    String degree ="";
    String degree2;
    String degree3;
    String degree4;
    String latitude ="";
    String longitude="";
    String lati="";
    String longi="";
    String town;
    String zipC = "";
    EditText zipCode;
    Button run;
    TextView lat;
    TextView longit;
    TextView city;
    TextView weath;
    TextView weath2;
    TextView weath3;
    TextView weath4;
    ImageView imag;
    ImageView imag2;
    ImageView imag3;
    ImageView imag4;
    TextView date;
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

    run.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AsyncTaskDownloadClassThing asyncTask = new AsyncTaskDownloadClassThing();
            asyncTask.execute();

            if (!(latitude.equals("") && longitude.equals(""))) {
                AsyncTaskDownloadClassThing2 asyncTask2 = new AsyncTaskDownloadClassThing2();
                asyncTask2.execute();
            }
        }
    });
}

// AsyncTask classes with weather fetching logic
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
            e.printStackTrace();
        }
        return null;
    }
}

public class AsyncTaskDownloadClassThing2 extends AsyncTask<Void, Void, Void> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL secondWeatherKey = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lati + "&lon=" + longi + "&exclude=daily,minutely,alerts,current&units=imperial&appid=dfb78e5904fad1458c4311b74909d54f");
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

            for (int i = 0; i < array.length(); i++) {
                SimpleDateFormat date = new SimpleDateFormat("hh:mm a");
                java.util.Date time = new java.util.Date((long) Integer.parseInt(array.getJSONObject(i).getString("dt")) * 10);
                String ti = date.format(time);
                arr.put(ti);
                arr.put(array.getJSONObject(i).getString("temp"));
                JSONArray weatherW = new JSONArray(array.getJSONObject(i).getString("weather"));
                arr.put(weatherW.getJSONObject(0).getString("description"));
            }

            // Update TextViews and Images
            runOnUiThread(() -> {
                weath.setText("Time: " + arr.get(0) + " Temp: " + arr.get(1) + "° Description: " + arr.get(2));
                weath2.setText("Time: " + arr.get(3) + " Temp: " + arr.get(4) + "° Description: " + arr.get(5));
                weath3.setText("Time: " + arr.get(6) + " Temp: " + arr.get(7) + "° Description: " + arr.get(8));
                weath4.setText("Time: " + arr.get(9) + " Temp: " + arr.get(10) + "° Description: " + arr.get(11));

                // Images
                if (arr.get(2).toString().contains("snow")) imag.setImageResource(R.drawable.snow);
                if (arr.get(5).toString().contains("snow")) imag2.setImageResource(R.drawable.snow);
                if (arr.get(8).toString().contains("snow")) imag3.setImageResource(R.drawable.snow);
                if (arr.get(11).toString().contains("snow")) imag4.setImageResource(R.drawable.snow);

                if (arr.get(2).toString().contains("clear")) imag.setImageResource(R.drawable.sky);
                if (arr.get(5).toString().contains("clear")) imag2.setImageResource(R.drawable.sky);
                if (arr.get(8).toString().contains("clear")) imag3.setImageResource(R.drawable.sky);
                if (arr.get(11).toString().contains("clear")) imag4.setImageResource(R.drawable.sky);
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}