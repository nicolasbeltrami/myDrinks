package com.nico.mydrinks.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

object RetrofitClient {
    val drinkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DrinkService::class.java)
    }


}