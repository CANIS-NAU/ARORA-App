package com.example.aorora.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static HttpLoggingInterceptor interceptor;
    private static OkHttpClient client;
    //"https://jsonplaceholder.typicode.com";
    //"https://aroraserver.com" ip adress :8000
    //"http://104.248.178.78:8000"
    private static final String BASE_URL = "http://104.248.178.78:5050";
    public static final String IP = "104.248.178.78";
    public static final Integer PORT = 5050;


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
