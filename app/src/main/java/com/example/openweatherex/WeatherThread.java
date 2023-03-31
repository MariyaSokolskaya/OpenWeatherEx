package com.example.openweatherex;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherThread extends Thread{
    Handler handler;
    OkHttpClient client;
    String cityName;

    public WeatherThread(Handler handler, String city){
        this.handler = handler;
        client = new OkHttpClient();
        cityName = city;
    }

    @Override
    public void run() {
        super.run();
        //создание строки запроса
        HttpUrl.Builder strBuilder = HttpUrl.parse(MainActivity.ADDRESS).newBuilder();
        //добавление параметров
        strBuilder.addQueryParameter("appid", MainActivity.APIKEY);
        strBuilder.addQueryParameter("q", cityName);
        strBuilder.addQueryParameter("units", "metric");
        strBuilder.addQueryParameter("lang", "ru");
        //построить строку запроса
        String url = strBuilder.build().toString();
        //создание запроса
        Request request = new Request.Builder().url(url).build();
        //отправка запроса
        try {
            Response response = client.newCall(request).execute();

            String res = response.body().string();
            //создание сообщение для handler
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("body", res);
            message.setData(bundle);
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
