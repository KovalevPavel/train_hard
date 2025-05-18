package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.ExercisesConfigDto
import trainhard.configs_data.generated.resources.Res

class ExercisesConfigDataProvider(
    private val json: Json,
) : ConfigDataProvider<ExercisesConfigDto> {
    override val provider: suspend () -> ExercisesConfigDto = {
        val string = Res.readBytes("files/exercises_config.json").decodeToString()
        json.decodeFromString<ExercisesConfigDto>(string)
    }
}
