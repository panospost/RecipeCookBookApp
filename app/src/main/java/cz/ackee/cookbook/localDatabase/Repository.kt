package cz.ackee.cookbook.localDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.NoConnectivityException
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import org.json.JSONObject



class Repository(var networkDataSource: NetworkRecipeDataSource,
                 var getAllRecipesDao: GetAllRecipesDao) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        _isInitialised.value = false
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

    fun getTheRecipeDetails(recipeId: String) {
        coroutineScope.launch {
            try {

                    recipeRequested = networkDataSource.getRecipeDetails(recipeId)
                    _isInitialised.value = true


            }catch (e: NoConnectivityException){
                Log.i("333Error", e.message)

            }
        }
    }
    fun clearIsInitialised(){
        _isInitialised.value = false
    }

    fun sendNewRating(recipe: DetailRecipeObject) {
        try {
            GlobalScope.launch {
                withContext(Dispatchers.IO){
                    networkDataSource.updateRecipe(recipe)
                }
            }
        }catch (e: Exception){
            Log.i("TAG", e.toString())
        }

    }
}