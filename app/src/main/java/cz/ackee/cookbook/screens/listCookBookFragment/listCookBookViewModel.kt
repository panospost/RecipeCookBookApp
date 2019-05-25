package cz.ackee.cookbook.screens.listCookBookFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.network.RecipesApi
import cz.ackee.cookbook.models.RecipesObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ListCookBookViewModel: ViewModel(){



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
        getAllRecipes()
       // _isInitialised.value = false;
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
     fun getAllRecipes() {

        coroutineScope.launch{
            val deferredProperties = RecipesApi.retrofitService.getAllRecipes()

            try {


                val listResult = deferredProperties.await()

                _responseList.value =  listResult

            }catch (t: Exception){
                Log.i("error", t.message)

            }

        }
    }

     fun getTheRecipeDetails(recipeId: String) {
        coroutineScope.launch {
            val getRecipeRequest =  RecipesApi.retrofitService.getOneRecipe(recipeId)
            try {
                recipeRequested = getRecipeRequest.await()
                Log.i("333RecipeName", recipeRequested.name)
                _isInitialised.value = true

            }catch (e: Exception){
                Log.i("333Error", e.message)

            }
        }
    }
    fun clearIsInitialised(){
//        _isInitialised.value = false
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}