package com.nico.mydrinks.network

import com.nico.mydrinks.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkService {

    @GET("search.php")
    suspend fun getDrinkByName(@Query("s") drinkName:String): DrinkList

}