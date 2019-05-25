package cz.ackee.cookbook.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class], version=1, exportSchema = false)
abstract class RecipesLocalDatabase: RoomDatabase(){
    abstract val getAllRecipesDao: GetAllRecipesDao

    companion object{

        @Volatile
        private var INSTANCE: RecipesLocalDatabase? = null

        fun getInstance(context: Context): RecipesLocalDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            RecipesLocalDatabase::class.java,
                            "recipes_local_database"
                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}