package com.nico.mydrinks.viewmodel

import androidx.lifecycle.*
import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.network.Repository
import com.nico.mydrinks.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    private val drinksData = MutableLiveData<String>()

    fun setDrink(drinkName:String){
        drinksData.value = drinkName
    }
    init {
        setDrink("Gin")
    }

    val fetchDrinksList = drinksData.distinctUntilChanged().switchMap {drinkName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repository.getDrinksList(drinkName))
            }catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun addFavorite(drink:DrinkEntity){
        viewModelScope.launch {
            repository.addFavoriteDrink(drink)
        }
    }

    fun getFavoriteDrinks() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getFavoriteDrinks())
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun delete(drink: DrinkEntity){
        viewModelScope.launch { repository.delete(drink) }
    }

}