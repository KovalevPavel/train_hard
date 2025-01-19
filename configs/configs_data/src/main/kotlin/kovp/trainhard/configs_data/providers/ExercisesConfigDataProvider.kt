package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.ExercisesConfigDto
import kovp.trainhard.configs_data.R
import trainhard.kovp.core.ResourceProvider

class ExercisesConfigDataProvider(
    private val resourceProvider: ResourceProvider,
    private val json: Json,
) : ConfigDataProvider<ExercisesConfigDto> {
    override val provider: () -> ExercisesConfigDto = {
        val rawString = resourceProvider.getConfig(R.raw.exercises_config)
        json.decodeFromString<ExercisesConfigDto>(rawString)
    }
}
