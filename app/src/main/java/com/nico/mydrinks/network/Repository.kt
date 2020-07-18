package com.nico.mydrinks.network

import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.resource.Resource

interface Repository {
   suspend fun getDrinksList(drinkName: String): Resource<List<Drink>>
   suspend fun getFavoriteDrinks(): Resource<List<DrinkEntity>>
   suspend fun addFavoriteDrink(drink: DrinkEntity)
   suspend fun delete(drink: DrinkEntity)
}