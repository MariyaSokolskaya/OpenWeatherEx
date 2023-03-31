package com.example.openweatherex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String APIKEY = "";
    public static final String ADDRESS = "https://api.openweathermap.org/data/2.5/weather";

    EditText cityName;
    Button getButton;
    TextView resultText;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.city_name);
        getButton = findViewById(R.id.get_button);
        resultText = findViewById(R.id.result_text);

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String res = msg.getData().getString("body");
                resultText.setText(res);
            }
        };

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherThread thread = new WeatherThread(
                        handler, cityName.getText().toString());
                thread.start();
            }
        });
    }
}