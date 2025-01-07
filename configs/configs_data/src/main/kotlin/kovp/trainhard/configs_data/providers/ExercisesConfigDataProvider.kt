package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.ExercisesConfigDto
import kovp.trainhard.configs_data.R
import trainhard.kovp.core.ResourceProvider

class ExercisesConfigDataProvider(
    private val resourceProvider: ResourceProvider,
) : ConfigDataProvider<ExercisesConfigDto> {
    override val provider: () -> ExercisesConfigDto = {
        val rawString = resourceProvider.getConfig(R.raw.exercises_config)
        Json.decodeFromString<ExercisesConfigDto>(rawString)
    }
}
