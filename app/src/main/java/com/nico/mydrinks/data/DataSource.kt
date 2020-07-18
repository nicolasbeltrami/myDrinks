package com.nico.mydrinks.data

import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.network.RetrofitClient
import com.nico.mydrinks.resource.Resource

class DataSource(private var appDatabase: AppDatabase) {

    suspend fun getDrinkByName(drinkName: String) : Resource<List<Drink>> {
        return Resource.Success(RetrofitClient.drinkService.getDrinkByName(drinkName).drinkList)
    }

    suspend fun addFavorite(drink: DrinkEntity){
        appDatabase.drinkDao().addFavorite(drink)
    }

    suspend fun getFavoriteDrinks(): Resource<List<DrinkEntity>>{
        return Resource.Success(appDatabase.drinkDao().getAllFavoriteDrinks())
    }

    suspend fun delete(drink: DrinkEntity){
        appDatabase.drinkDao().delete(drink)
    }

}