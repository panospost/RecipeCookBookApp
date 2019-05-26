package cz.ackee.cookbook.localDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.NoConnectivityException
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException

class Repository(var networkDataSource: NetworkRecipeDataSource,
                 var getAllRecipesDao: GetAllRecipesDao) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _responseList = MutableLiveData<List<RecipesObject>>()
    // The external immutable LiveData for the request status String
    val responseList: LiveData<List<RecipesObject>>
        get() = _responseList

    init {
        try {
            coroutineScope.launch {
                getCurrentRecipes()
            }
        }catch (e: NullPointerException) {
            Log.i("null", e.message)
        }catch (e: Exception){
            Log.i("null", e.message)

        }
    }


     suspend fun getCurrentRecipes() {
         getLocalData()
          try {
             _responseList.value = networkDataSource.getAllRecipes()
         }catch (e: NoConnectivityException){
              Log.i("NOINTERNET", "stay with the local")
         }
    }

    private  fun getLocalData() {
        getAllRecipesDao.getAllRecipes().observeForever{
            _responseList.value = it
        }
    }

     fun cacheRecipes( recipes: List<RecipesObject>){
         GlobalScope.launch {
              withContext(Dispatchers.IO){
                 getAllRecipesDao.insertAll(recipes)

             }
         }

    }


}