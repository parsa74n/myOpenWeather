package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;



import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import org.json.JSONArray;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;




public class MainActivity extends AppCompatActivity {

    EditText cityEdit;
    Button searchButton;
    ConstraintLayout cs;
    String cityName;
    OkHttpClient client;
    String country;
    HttpUrl.Builder urlBuilder;
    double temp;
    String cloud;
    double wind;
    int press;
    int hum;
    String mainWeth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEdit = findViewById(R.id.cityEdit);
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        cs=findViewById(R.id.cs);


        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        urlBuilder = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                flashButton();


            }


        });

    }
    public void flashButton(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                searchButton.setEnabled(false);
                urlBuilder.addQueryParameter("q", cityEdit.getText().toString());
                urlBuilder.addQueryParameter("appid", "111ef4c40db0aa2c812d2776bbfdc0ff");
                String url = urlBuilder.build().toString();
                progressBar.setVisibility(View.VISIBLE);
                final Request request = new Request.Builder()
                        .url(url)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response res) {
                        try {
                            ResponseBody response = res.body();
                            if (!res.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(i);
                                finish();

                                //throw new IOException("Unexpected code " + res);
                            }

                            JSONObject resObject = new JSONObject(response.string());


                            cityName = resObject.getString("name");
                            JSONObject sys = resObject.getJSONObject("sys");
                            country = sys.getString("country");

                            JSONObject main = resObject.getJSONObject("main");
                            temp = main.getDouble("temp");
                            press = main.getInt("pressure");
                            hum = main.getInt("humidity");

                            JSONObject w = resObject.getJSONObject("wind");
                            wind = w.getDouble("speed");

                            JSONArray array = resObject.getJSONArray("weather");
                            cloud = array.getJSONObject(0).getString("description");
                            mainWeth = array.getJSONObject(0).getString("main");

                            Intent i = new Intent(getApplicationContext(), ShowActivity.class);
                            i.putExtra("cityName", cityName);
                            i.putExtra("temp", temp);
                            i.putExtra("press", press);
                            i.putExtra("hum", hum);
                            i.putExtra("wind", wind);
                            i.putExtra("main", mainWeth);
                            i.putExtra("country", country);
                            i.putExtra("cloud", cloud);
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(i);
                            finish();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
