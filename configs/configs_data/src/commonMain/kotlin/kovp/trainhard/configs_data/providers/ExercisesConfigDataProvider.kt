package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.ExercisesConfigDto
//import kovp.trainhard.configs_data.R
import kovp.trainhard.core.ResourceProvider

class ExercisesConfigDataProvider(
    private val resourceProvider: ResourceProvider,
    private val json: Json,
) : ConfigDataProvider<ExercisesConfigDto> {
    override val provider: suspend () -> ExercisesConfigDto = {
//        val rawString = resourceProvider.getConfig(R.raw.exercises_config)
//        json.decodeFromString<ExercisesConfigDto>(rawString)
        ExercisesConfigDto(
            muscleGroups = null,
            muscles = emptyMap(),
            defaultExercises = emptyList()
        )
    }
}
