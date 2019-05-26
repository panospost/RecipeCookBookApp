package cz.ackee.cookbook.network


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

}