package cz.ackee.cookbook.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.models.DetailRecipeObject
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://cookbook.ack.ee/api/v1/"
enum class RecipeApiFilter(val value: String) { SHOW_ALL("rent"), ADD_RECIPE("buy") }

/**
 * Build the Moshi object that Retrofit will be using,
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


interface RecipesApiService {
    @GET("recipes/")
    fun getAllRecipes():
            Deferred<List<RecipesObject>>

    @POST("recipes")
    fun postRecipes(@Body recipesObject: DetailRecipeObject): Deferred<String>

    @GET("recipes/{recipeId}")
    fun getOneRecipe(@Path("recipeId")  id: String):
            Deferred<DetailRecipeObject>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RecipesApi {
    val retrofitService : RecipesApiService by lazy { retrofit.create(RecipesApiService::class.java) }
}