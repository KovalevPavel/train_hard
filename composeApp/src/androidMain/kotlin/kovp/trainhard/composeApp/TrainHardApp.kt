package kovp.trainhard.composeApp

import android.app.Application
import io.paperdb.Paper
import kovp.trainhard.configs_api.configsModule
import kovp.trainhard.composeApp.di.coreModule
import kovp.trainhard.composeApp.di.initializationModule
import kovp.trainhard.core_storage.di.storageModule
import kovp.trainhard.database.di.getDatabaseModule
import kovp.trainhard.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class TrainHardApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDb()
        initLogging()
        initDi()
    }

    private fun initDb() {
        Paper.init(this@TrainHardApp)
    }

    private fun initDi() {
        startKoin {
            androidContext(this@TrainHardApp)
            modules(
                coreModule,
                configsModule,
                getDatabaseModule(getDatabaseBuilder(this@TrainHardApp)),
                initializationModule,
                storageModule(context = this@TrainHardApp),
            )
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
