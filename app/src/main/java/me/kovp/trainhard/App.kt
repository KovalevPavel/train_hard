package me.kovp.trainhard

import android.app.Application
import me.kovp.trainhard.core_storage.di.storageModule
import me.kovp.trainhard.database.di.dbModule
import me.kovp.trainhard.di.initializationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@App)
            modules(
                dbModule,
                initializationModule,
                storageModule(context = this@App),
            )
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
