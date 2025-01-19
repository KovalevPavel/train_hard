package kovp.trainhard.configs_api

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_data.ConfigHolderImpl
import kovp.trainhard.configs_data.mappers.ExercisesConfigMapper
import kovp.trainhard.configs_data.mappers.HomeScreenConfigMapper
import kovp.trainhard.configs_data.mappers.TrainingConfigMapper
import kovp.trainhard.configs_data.providers.ExercisesConfigDataProvider
import kovp.trainhard.configs_data.providers.HomeScreenConfigDataProvider
import kovp.trainhard.configs_data.providers.TrainingConfigDataProvider
import org.koin.dsl.module

val configsModule = module {
    single { ExercisesConfigDataProvider(resourceProvider = get(), json = get()) }
    single { TrainingConfigDataProvider(resourceProvider = get(), json = get()) }
    single { HomeScreenConfigDataProvider(resourceProvider = get(), json = get()) }
    single { ExercisesConfigMapper() }
    single { TrainingConfigMapper() }
    single { HomeScreenConfigMapper() }
    single<ConfigHolder> {
        ConfigHolderImpl(
            exercisesConfigDataProvider = get(),
            trainingConfigDataProvider = get(),
            homeScreenConfigDataProvider = get(),
            exercisesConfigMapper = get(),
            trainingConfigMapper = get(),
            homeScreenConfigMapper = get(),
        )
    }
}
