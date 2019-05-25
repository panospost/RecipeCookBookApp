package cz.ackee.cookbook.localDatabase
import cz.ackee.cookbook.models.RecipesObject
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(var networkDataSource: NetworkRecipeDataSource,
                 var getAllRecipesDao: GetAllRecipesDao) {

     suspend fun getCurrentRecipes(): List<RecipesObject> {
        return withContext(Dispatchers.IO) {
            networkDataSource.getAllRecipes()
        }
    }




}