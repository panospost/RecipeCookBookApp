package cz.ackee.cookbook.screens.addScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import kotlinx.coroutines.*
import java.lang.Exception

class AddFragmentViewModel(val nwDataSource: NetworkRecipeDataSource) : ViewModel() {

    var recipeToSend = DetailRecipeObject(null,"Ackee", 0, "cwe", "wefdw", mutableListOf("ackee", "something"), 0)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _requestResult = MutableLiveData<String>()
    val requestResult: LiveData<String>
    get() = _requestResult

    fun sendRecipe(recipeToSend: DetailRecipeObject) {

        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO){
                    nwDataSource.postRecipe(recipeToSend)
                }
            }catch (e: Exception){
                _requestResult.value = "BAD"
                Log.i("error112", "Bad")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}