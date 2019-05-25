package cz.ackee.cookbook.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject

class NetworkRecipeDataSource(var recipesApiService: RecipesApiService) {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _responseList = MutableLiveData<List<RecipesObject>>()
    // The external immutable LiveData for the request status String
    val responseList: LiveData<List<RecipesObject>>
        get() = _responseList

    suspend fun getAllRecipes(): List<RecipesObject> {
            val deferredRecipes = recipesApiService.getAllRecipes()
            deferredRecipes.await()
            _responseList.value = deferredRecipes.await()
        return  deferredRecipes.await()
    }

    suspend fun getRecipeDetails(recipeId: String): DetailRecipeObject {
       return  recipesApiService.getOneRecipe(recipeId).await()
    }

}