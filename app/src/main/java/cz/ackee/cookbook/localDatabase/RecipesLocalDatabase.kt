package cz.ackee.cookbook.localDatabase

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cz.ackee.cookbook.models.RecipesObject
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


@Database(entities = [RecipesObject::class], version = 3, exportSchema = false)
abstract class RecipesLocalDatabase : RoomDatabase() {

    abstract val getAllRecipesDao: GetAllRecipesDao


    companion object {

        @Volatile
        private var INSTANCE: RecipesLocalDatabase? = null

        fun getInstance(context: Context): RecipesLocalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
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


        fun getDao(context: Context): GetAllRecipesDao? {
            return getInstance(context).getAllRecipesDao
        }
    }

}


@Module
class getInstanceDb (var context: Context){



    @Provides
    @Singleton
    fun getDb(): RecipesLocalDatabase{
        return RecipesLocalDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getDao(): GetAllRecipesDao{
        return RecipesLocalDatabase.getInstance(context).getAllRecipesDao
    }

}

