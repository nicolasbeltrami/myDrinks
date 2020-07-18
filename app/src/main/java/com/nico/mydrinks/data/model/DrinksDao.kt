package com.nico.mydrinks.data.model

import androidx.room.*

@Dao
interface DrinksDao {

    @Query("SELECT * FROM drink_entity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(drink:DrinkEntity)

    @Delete
    suspend fun delete(drink: DrinkEntity)
}