package kovp.trainhard.configs_data

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_data.mappers.ExercisesConfigMapper
import kovp.trainhard.configs_data.providers.ExercisesConfigDataProvider

class ConfigHolderImpl(
    private val exercisesConfigDataProvider: ExercisesConfigDataProvider,
    private val exercisesConfigMapper: ExercisesConfigMapper,
) : ConfigHolder {

    override val exercises: ExercisesConfig by lazy {
        exercisesConfigDataProvider.provider().let(exercisesConfigMapper::mapConfig)
    }
}
