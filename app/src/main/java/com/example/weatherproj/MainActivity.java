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

}