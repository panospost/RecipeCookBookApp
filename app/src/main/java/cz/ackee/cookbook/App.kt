package cz.ackee.cookbook

import android.app.Application
import android.os.Build
import androidx.work.*
import cz.ackee.cookbook.di.AppComponent
import cz.ackee.cookbook.di.AppModule
import cz.ackee.cookbook.di.DaggerAppComponent
import cz.ackee.cookbook.localDatabase.getInstanceDb
import cz.ackee.cookbook.worker.BackGroundWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Main application class
 */
class App : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        lateinit var allComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        allComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .getInstanceDb(getInstanceDb(this))
                .build()
        cacheData()
    }

    private fun cacheData() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<BackGroundWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                BackGroundWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }

}
