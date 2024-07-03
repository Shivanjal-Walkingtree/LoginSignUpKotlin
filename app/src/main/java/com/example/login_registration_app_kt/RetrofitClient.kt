package com.example.login_registration_app_kt

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://teamtracker-dev.azurewebsites.net/api/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

