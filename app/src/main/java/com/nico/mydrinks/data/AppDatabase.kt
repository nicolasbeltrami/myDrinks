package com.nico.mydrinks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.data.model.DrinksDao

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinksDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "drinks_table")
                .build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }
}