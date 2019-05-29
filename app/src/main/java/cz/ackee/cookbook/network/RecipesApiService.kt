package cz.ackee.cookbook.network

import android.content.Context
import android.util.Log
import androidx.paging.PagedList
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.models.DetailRecipeObject
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton


private const val BASE_URL = "https://cookbook.ack.ee/api/v1/"


fun getRecipes(
        service: RecipesApiService,
        limit: Int,
        offset: Int,
        onSuccess: (repos: List<RecipesObject>?) -> Unit,
        onError: (error: String) -> Unit
) {

    service.getAllRecipes(limit, offset).enqueue(
            object : Callback<List<RecipesObject>> {
                override fun onFailure(call: Call<List<RecipesObject>>, t: Throwable) {
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                        call: Call<List<RecipesObject>>?,
                        response: Response<List<RecipesObject>>
                ) {
                    Log.d("responseSucc", "got a response $response")
                    if (response.isSuccessful) {
                        val recipes = response.body()
                        onSuccess(recipes)
                    } else {
                        Log.i("error", response.errorBody()?.string())
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )
}

@Module
interface RecipesApiService {
    @GET("recipes")
    fun getAllRecipes(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
    ): Call<List<RecipesObject>>

    @POST("recipes")
    fun postRecipes(@Body recipesObject: DetailRecipeObject): Call<Int>

    @GET("recipes/{recipeId}")
    fun getOneRecipe(@Path("recipeId") id: String):
            Deferred<DetailRecipeObject>

    @PUT("recipes/{recipeId}")
    fun updateRecipe(@Path("recipeId") id: String, @Body recipesObject: DetailRecipeObject)
            : Call<Int>

    @POST("recipes/{recipeId}/ratings")
    fun updateScoreRecipe(@Path("recipeId") id: String, @Body score: Int)
            : Call<Int>


}
@Module
class RetrofitObject{

    @Singleton
    @Provides
    fun invoke(
            context: Context
    ): RecipesApiService {
        /**
         * Build the Moshi object that Retrofit will be using,
         */
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        val client = OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor(context))
                .build()

        /**
         * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
         * object.
         */
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
                .create(RecipesApiService::class.java)
    }
}
