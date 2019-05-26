package cz.ackee.cookbook.screens.listCookBookFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject


class ListCookBookViewModel(val repository: Repository) : ViewModel() {


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _responseList = MutableLiveData<List<RecipesObject>>()
    // The external immutable LiveData for the request status String
    val responseList: LiveData<List<RecipesObject>>
        get() = _responseList


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _isInitialised = MutableLiveData<Boolean>()
    // The external immutable LiveData for the request status String
    val isInitialised: LiveData<Boolean>
        get() = _isInitialised


    init {
          repository.responseList.observeForever {
           _responseList.value = it
        }

        repository.isInitialised.observeForever {
            if(it){
                _isInitialised.value = it
            }

        }
    }

    fun saveRecipes(recipes: List<RecipesObject>) {
        repository.cacheRecipes(recipes)

    }


    fun getTheRecipeDetails(recipeId: String) {
             repository.getTheRecipeDetails(recipeId)
    }

    fun getSpecificRecipe(): DetailRecipeObject{
        return repository.recipeRequested
    }

    fun clearIsInitialised() {
        _isInitialised.value = false
        repository.clearIsInitialised()

    }


}