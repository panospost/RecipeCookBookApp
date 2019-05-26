package cz.ackee.cookbook.network


import android.util.Log
import com.squareup.moshi.JsonDataException
import cz.ackee.cookbook.models.DetailRecipeObject
import cz.ackee.cookbook.models.RecipesObject

class NetworkRecipeDataSource(var recipesApiService: RecipesApiService) {



    suspend fun getAllRecipes(): List<RecipesObject> {
            val deferredRecipes = recipesApiService.getAllRecipes()
            deferredRecipes.await()
        return  deferredRecipes.await()
    }

    suspend fun getRecipeDetails(recipeId: String): DetailRecipeObject {
       return  recipesApiService.getOneRecipe(recipeId).await()
    }

     suspend fun updateRecipe(recipe: DetailRecipeObject) {
            var x =recipesApiService.updateRecipe(recipe.id!!, recipe)
         Log.i("tag", x.toString())
    }

}