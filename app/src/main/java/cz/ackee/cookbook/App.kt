package cz.ackee.cookbook

import android.app.Application
import android.os.Build
import androidx.work.*
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



    override fun onCreate() {
        super.onCreate()
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
