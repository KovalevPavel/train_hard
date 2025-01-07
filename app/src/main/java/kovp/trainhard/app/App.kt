package kovp.trainhard.app

import android.app.Application
import kovp.trainhard.app.di.configsModule
import kovp.trainhard.app.di.coreModule
import kovp.trainhard.app.di.initializationModule
import kovp.trainhard.core_storage.di.storageModule
import kovp.trainhard.database.di.dbModule
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
                coreModule,
                configsModule,
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
