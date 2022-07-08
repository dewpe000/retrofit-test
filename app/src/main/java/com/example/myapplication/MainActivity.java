package com.example.myapplication;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URISyntaxException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    String BASE_URL = "http://172.10.5.52:443/";
    Retrofit retrofit;
    RetrofitService service;


    private Button getBtn, postBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        service = retrofit.create(RetrofitService.class);

        getBtn = findViewById(R.id.getBtn);
        postBtn = findViewById(R.id.postBtn);

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<UserData>> call_get = service.getUD("get");
                call_get.enqueue(new Callback<List<UserData>>() {
                    @Override
                    public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                        if(response.isSuccessful()) {
                            try {
                                String result = response.body().toString();
                                Log.d("GET", result);
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.d("GET", "error = " + String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<List<UserData>> call, Throwable t) {
                        Log.d ("GET", "Fail" + t.getMessage());
                    }
                });
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<UserData> call_post = service.postUD("post_test_name", "post_test_id", "post_test_pw");
                call_post.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if(response.isSuccessful()) {
                            try {
                                String result = response.body().toString();
                                Log.d("POST", result);
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);

                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.d("POST", "error = " + String.valueOf(response.code()) + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Log.d ("POST", "Fail");
                    }
                });
            }
        });
    }
}