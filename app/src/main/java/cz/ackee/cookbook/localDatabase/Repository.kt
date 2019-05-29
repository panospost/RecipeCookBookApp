package cz.ackee.cookbook.localDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.network.BoundaryCallback
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.NoConnectivityException
import dagger.Module
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import org.json.JSONObject
import javax.inject.Inject

@Module
class Repository @Inject constructor(var networkDataSource: NetworkRecipeDataSource, var localDB: GetAllRecipesDao) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The internal MutableLiveData String that stores the status of the most recent request
    //private val _responseList = MutableLiveData<PagedList<RecipesObject>>()
    // The external immutable LiveData for the request status String
   lateinit var responseList: LiveData<PagedList<RecipesObject>>


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _isInitialised = MutableLiveData<Boolean>()
    // The external immutable LiveData for the request status String
    val isInitialised: LiveData<Boolean>
        get() = _isInitialised

    lateinit var recipeRequested: DetailRecipeObject


      fun getLocalData() {

              val dataSourceFactory = localDB.getAllRecipes()
              val boundaryCallback = BoundaryCallback(networkDataSource.recipesApiService) { recipeslocal ->
                    Log.i("iamcalled", "myrecipes")
                  GlobalScope.launch {
                      withContext(Dispatchers.IO) {
                          cacheRecipes(recipeslocal)
                      }
                  }

              }
              // Get the paged list
          responseList = LivePagedListBuilder(dataSourceFactory, 20)
                      .setBoundaryCallback(boundaryCallback)
                      .build()
    }

       suspend fun cacheRecipes( recipes: List<RecipesObject>){
         GlobalScope.launch {
              withContext(Dispatchers.IO){
                  localDB.insertAll(recipes)

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
                    networkDataSource.updateScore(recipe)
                }
            }
        }catch (e: Exception){
            Log.i("TAG", e.toString())
        }

    }
}