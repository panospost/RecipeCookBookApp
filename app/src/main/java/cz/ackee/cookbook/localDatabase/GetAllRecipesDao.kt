package cz.ackee.cookbook.localDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GetAllRecipesDao{

    @Insert
    fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipies: List<RecipeEntity>)

    @Update
    fun update(recipe: RecipeEntity)

    @Query(value = "SELECT * FROM  local_recipes_database WHERE id=:key")
    fun getSpecificRecipe(key: String): RecipeEntity

    @Query(value = "SELECT * FROM  local_recipes_database WHERE id=:key")
    fun getNightWithId(key: String): LiveData<RecipeEntity>

    @Query(value = "DELETE from local_recipes_database")
    fun clear()

    @Query("SELECT * FROM local_recipes_database ORDER BY id DESC")
    fun getAllRecipes(): LiveData<List<RecipeEntity>>

}