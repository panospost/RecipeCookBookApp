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

    lateinit var recipeRequested: DetailRecipeObject

    init {
          repository.responseList.observeForever {
           _responseList.value = it
        }
    }

    fun saveRecipes(recipes: List<RecipesObject>) {
        repository.cacheRecipes(recipes)

    }


    //     fun getTheRecipeDetails(recipeId: String) {
//        coroutineScope.launch {
//
//            try {
//                recipeRequested = netWorkRecipes.getRecipeDetails(recipeId)
//                _isInitialised.value = true
//
//            }catch (e: NoConnectivityException){
//                Log.i("333Error", e.message)
//
//            }
//        }
//    }
//    fun clearIsInitialised(){
////        _isInitialised.value = false
//    }
//
//
    override fun onCleared() {
        super.onCleared()
       // repository.responseList.removeObservers(this)
    }
}