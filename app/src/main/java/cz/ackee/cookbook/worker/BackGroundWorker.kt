package cz.ackee.cookbook.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cz.ackee.cookbook.localDatabase.RecipesLocalDatabase
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.RecipesApiService
import retrofit2.HttpException

class BackGroundWorker(var appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Payload {

        val recipesApiService = RecipesApiService(appContext)
       val networkRecipeDataSource = NetworkRecipeDataSource(recipesApiService)
        val repository = Repository(networkRecipeDataSource, RecipesLocalDatabase.getInstance(appContext).getAllRecipesDao)

        return try {
            repository.getLocalData()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }

}