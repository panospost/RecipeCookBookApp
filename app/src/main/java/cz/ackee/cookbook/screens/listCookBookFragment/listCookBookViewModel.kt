package cz.ackee.cookbook.screens.listCookBookFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.network.NoConnectivityException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListCookBookViewModel(val repository: Repository): ViewModel(){



    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )


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
           try {
               coroutineScope.launch {
                   _responseList.value = repository.getCurrentRecipes()
               }
           }catch (e: NoConnectivityException){
               Log.i("2143NoConnection", "no internet get local data")
           }
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
        viewModelJob.cancel()
    }
}