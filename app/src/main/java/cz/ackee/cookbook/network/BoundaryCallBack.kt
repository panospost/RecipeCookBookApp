package cz.ackee.cookbook.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import cz.ackee.cookbook.models.RecipesObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoundaryCallback(
        private val service: RecipesApiService,
         val cacheRecipes: (recipes: List<RecipesObject>) -> Unit
) : PagedList.BoundaryCallback<RecipesObject>() {
    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: RecipesObject) {
        requestAndSaveData()
    }
    init {
        requestAndSaveData()
    }
    // keep the last requested page.
// When the request is successful, increment the page number.
    private var offset = 0

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    companion object {
        private const val NETWORK_PAGE_SIZE = 10

    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                getRecipes(service, NETWORK_PAGE_SIZE, offset ,{ recipes ->
                    offset+= NETWORK_PAGE_SIZE
                    isRequestInProgress = false
                    recipes?.let{
                        cacheRecipes(recipes)
                    }
                }, { error ->
                    _networkErrors.postValue(error)
                    isRequestInProgress = false
                })
            }
        }

    }

}