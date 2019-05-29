package cz.ackee.cookbook.di

import android.app.Application
import android.content.Context
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule( var mApplication: Application) {

    @Provides
    @Singleton
     fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
     fun providesApplicationContext(): Context {
        return mApplication
    }
}