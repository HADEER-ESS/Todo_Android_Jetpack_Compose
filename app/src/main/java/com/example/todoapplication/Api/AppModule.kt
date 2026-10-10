package com.example.todoapplication.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    // 10.0.2.2 is the special IP address used by the Android Emulator 
    // to access 'localhost' on your host machine.
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
