package com.nico.mydrinks.network

import com.nico.mydrinks.data.DataSource
import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.resource.Resource

class RepositoryImplementation(private val dataSource: DataSource) : Repository {
    override suspend fun getDrinksList(drinkName: String): Resource<List<Drink>> {
        return dataSource.getDrinkByName(drinkName)
    }

    override suspend fun getFavoriteDrinks(): Resource<List<DrinkEntity>> {
        return dataSource.getFavoriteDrinks()
    }

    override suspend fun addFavoriteDrink(drink: DrinkEntity) {
        dataSource.addFavorite(drink)
    }

    override suspend fun delete(drink: DrinkEntity) {
        dataSource.delete(drink)
    }

}