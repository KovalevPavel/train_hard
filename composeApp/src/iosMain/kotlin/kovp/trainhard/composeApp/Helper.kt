package kovp.trainhard.composeApp

import kovp.trainhard.composeApp.di.coreModule
import kovp.trainhard.composeApp.di.initializationModule
import kovp.trainhard.configs_api.configsModule
import kovp.trainhard.core_storage.di.storageModule
import kovp.trainhard.database.di.getDatabaseModule
import kovp.trainhard.database.getDatabaseBuilder
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun initKoin() {
    startKoin {
        printLogger(level = Level.DEBUG)
        modules(
            coreModule,
            configsModule,
            getDatabaseModule(getDatabaseBuilder()),
            initializationModule,
            storageModule(),
        )
    }
}
