package cz.ackee.cookbook.screens.addScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.network.RecipesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class AddFragmentViewModel : ViewModel() {

    var recipeToSend = DetailRecipeObject("Ackee", 0, "cwe", "wefdw", mutableListOf("ackee", "something"), 0)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _requestResult = MutableLiveData<String>()
    val requestResult: LiveData<String>
    get() = _requestResult

    fun sendRecipe(recipeToSend: DetailRecipeObject) {

        coroutineScope.launch {

            val call = RecipesApi.retrofitService.postRecipes(
                   recipeToSend)
            try {
                _requestResult.value = call.await()
                Log.i("response", _requestResult.value)
            }catch (e: Exception){
                _requestResult.value = "BAD"
                Log.i("error112", e.message)
            }


        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}