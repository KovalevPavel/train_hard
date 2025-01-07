package kovp.trainhard.configs_api

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_data.ConfigHolderImpl
import kovp.trainhard.configs_data.mappers.ExercisesConfigMapper
import kovp.trainhard.configs_data.providers.ExercisesConfigDataProvider
import org.koin.dsl.module

val configsModule = module {
    single { ExercisesConfigDataProvider(resourceProvider = get()) }
    single { ExercisesConfigMapper() }
    single<ConfigHolder> {
        ConfigHolderImpl(
            exercisesConfigMapper = get(),
            exercisesConfigDataProvider = get(),
        )
    }
}
