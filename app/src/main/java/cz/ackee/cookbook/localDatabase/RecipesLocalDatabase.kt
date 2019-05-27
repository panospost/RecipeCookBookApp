package cz.ackee.cookbook.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.ackee.cookbook.models.RecipesObject

@Database(entities = [RecipesObject::class], version=3, exportSchema = false)
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
                            "recipes_local_database_version"
                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}