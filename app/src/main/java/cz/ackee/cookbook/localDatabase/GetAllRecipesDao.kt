package cz.ackee.cookbook.localDatabase

import androidx.paging.DataSource
import androidx.room.*
import cz.ackee.cookbook.models.RecipesObject


@Dao
interface GetAllRecipesDao{

    @Insert
    fun insert(recipe: RecipesObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipies: List<RecipesObject>)

    @Update
    fun update(recipe: RecipesObject)

    @Query(value = "SELECT * FROM  recipes_local_database_version WHERE id=:key")
    fun getSpecificRecipe(key: String): RecipesObject


    @Query(value = "DELETE from recipes_local_database_version")
    fun clear()

    @Query("SELECT * FROM recipes_local_database_version")
    fun getAllRecipes(): DataSource.Factory<Int, RecipesObject>

}