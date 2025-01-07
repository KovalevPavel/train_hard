package kovp.trainhard.app.di

import kovp.trainhard.app.data.ConfigRepositoryImpl
import kovp.trainhard.app.domain.ConfigRepository
import org.koin.dsl.module

val configsModule = module {
    single<ConfigRepository> { ConfigRepositoryImpl(resourceProvider = get()) }
}
