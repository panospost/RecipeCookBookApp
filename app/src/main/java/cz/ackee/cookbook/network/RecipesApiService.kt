package cz.ackee.cookbook.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.models.DetailRecipeObject
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient



private const val BASE_URL = "https://cookbook.ack.ee/api/v1/"





interface RecipesApiService {
    @GET("recipes/")
    fun getAllRecipes():
            Deferred<List<RecipesObject>>

    @POST("recipes")
    fun postRecipes(@Body recipesObject: DetailRecipeObject): Deferred<String>

    @GET("recipes/{recipeId}")
    fun getOneRecipe(@Path("recipeId") id: String):
            Deferred<DetailRecipeObject>

    companion object {
        operator fun invoke(
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
            return  Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(RecipesApiService::class.java)
        }
    }
}

///**
// * A public Api object that exposes the lazy-initialized Retrofit service
// */
//object RecipesApi {
//    val retrofitService : RecipesApiService by lazy { retrofit.create(RecipesApiService::class.java) }
//}