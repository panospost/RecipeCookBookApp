package cz.ackee.cookbook.network



import android.util.Log
import cz.ackee.cookbook.models.DetailRecipeObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class NetworkRecipeDataSource(var recipesApiService: RecipesApiService) {


    suspend fun getRecipeDetails(recipeId: String): DetailRecipeObject {
       return  recipesApiService.getOneRecipe(recipeId).await()
    }

      fun updateScore(recipe: DetailRecipeObject) {

            val x =recipesApiService.updateScoreRecipe(recipe.id!!, recipe.score).enqueue(object :  retrofit2.Callback<Int> {
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.i("recipeUpdError", "recipe is not updated")
                }

                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    Log.i("recipeUpdSuccess", "recipe is updated")
                }

            })
    }

    fun postRecipe(recipe: DetailRecipeObject) {
        val x =recipesApiService.updateRecipe(recipe.id!!, recipe).enqueue(object :  retrofit2.Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("recipeUpdError", "recipe is not updated")
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.i("recipeUpdSuccess", "recipe is updated")
            }

        })
    }

}