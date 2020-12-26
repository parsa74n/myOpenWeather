package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ShowActivity extends AppCompatActivity {
    TextView cityName;
    TextView country;
    TextView hum;
    TextView cloud;
    TextView temp;
    TextView press;
    TextView wind;
    ImageView imageView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imageView = findViewById(R.id.imageView);
        cityName = findViewById(R.id.cityName);
        country = findViewById(R.id.country);
        hum = findViewById(R.id.hum);
        cloud = findViewById(R.id.cloud);
        press = findViewById(R.id.press);
        wind = findViewById(R.id.wind);
        temp = findViewById(R.id.temp);
        intent = getIntent();
        cityName.setText(intent.getStringExtra("cityName"));
        country.setText(intent.getStringExtra("country"));
        double t = intent.getDoubleExtra("temp", 0.0);
        t = t - 273.15;
        int tempeture=(int)t;
        String te=tempeture+"";
        temp.setText(te + "°C");
        press.setText(intent.getIntExtra("press", 0) + " hpa");
        hum.setText(intent.getIntExtra("hum", 0) + " %");
        t=intent.getDoubleExtra("wind", 0.0);
        te=String.format("%.2f",t);
        wind.setText( te+ " m/s");
        String cloudDes=intent.getStringExtra("cloud");
        cloud.setText(cloudDes);

        String main = intent.getStringExtra("main");
        if (main.equals("Rain"))
            imageView.setImageResource(R.drawable.rain);
        else if (main.equals("Mist")||main.equals("Smoke")||main.equals("Haze")||main.equals("Dust")||main.equals("Fog")||
                main.equals("Sand")||main.equals("Ash")||main.equals("Squall")||main.equals("Tornado"))
            imageView.setImageResource(R.drawable.mist);
        else if (main.equals("Snow"))
            imageView.setImageResource(R.drawable.snow);
        else if (main.equals("Drizzle"))
            imageView.setImageResource(R.drawable.drizzle);
        else if (main.equals("Thunderstorm"))
            imageView.setImageResource(R.drawable.thunderstorm);

        if (cloudDes.equals("broken clouds")||cloudDes.equals("overcast clouds"))
            imageView.setImageResource(R.drawable.broken_clouds);
        else if (cloudDes.equals("clear sky"))
            imageView.setImageResource(R.drawable.sky_is_clear);
        else if (cloudDes.equals("scattered clouds"))
            imageView.setImageResource(R.drawable.scattered_clouds);
        else if (cloudDes.equals("few clouds"))
            imageView.setImageResource(R.drawable.few_clouds);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}