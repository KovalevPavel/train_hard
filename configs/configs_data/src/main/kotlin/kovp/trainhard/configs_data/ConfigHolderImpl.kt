package kovp.trainhard.configs_data

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_core.HomeScreenConfig
import kovp.trainhard.configs_core.TrainingConfig
import kovp.trainhard.configs_data.mappers.ExercisesConfigMapper
import kovp.trainhard.configs_data.mappers.HomeScreenConfigMapper
import kovp.trainhard.configs_data.mappers.TrainingConfigMapper
import kovp.trainhard.configs_data.providers.ExercisesConfigDataProvider
import kovp.trainhard.configs_data.providers.HomeScreenConfigDataProvider
import kovp.trainhard.configs_data.providers.TrainingConfigDataProvider

class ConfigHolderImpl(
    private val exercisesConfigDataProvider: ExercisesConfigDataProvider,
    private val trainingConfigDataProvider: TrainingConfigDataProvider,
    private val homeScreenConfigDataProvider: HomeScreenConfigDataProvider,
    private val exercisesConfigMapper: ExercisesConfigMapper,
    private val trainingConfigMapper: TrainingConfigMapper,
    private val homeScreenConfigMapper: HomeScreenConfigMapper,
) : ConfigHolder {

    override val exercisesConfig: ExercisesConfig by lazy {
        exercisesConfigDataProvider.provider().let(exercisesConfigMapper::mapConfig)
    }

    override val trainingConfig: TrainingConfig by lazy {
        trainingConfigDataProvider.provider().let(trainingConfigMapper::mapConfig)
    }

    override val homeScreenConfig: HomeScreenConfig by lazy {
        homeScreenConfigDataProvider.provider().let(homeScreenConfigMapper::mapConfig)
    }
}
