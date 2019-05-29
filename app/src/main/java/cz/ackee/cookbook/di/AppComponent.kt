package cz.ackee.cookbook.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.localDatabase.getInstanceDb
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.RetrofitObject
import cz.ackee.cookbook.screens.addScreen.AddFragmentScreen
import cz.ackee.cookbook.screens.detailsScreen.DetailsFragment
import cz.ackee.cookbook.screens.listCookBookFragment.ListCookBookFragment


@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        RetrofitObject::class,
        getInstanceDb::class,
        Repository::class,
        NetworkRecipeDataSource::class)
)
interface AppComponent {
    fun inject(appModule: AppModule)
    fun inject(listFragment: ListCookBookFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(addFragment: AddFragmentScreen)
}