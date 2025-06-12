package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.TrainingConfigDto
import trainhard.configs_data.generated.resources.Res

class TrainingConfigDataProvider(
    private val json: Json,
): ConfigDataProvider<TrainingConfigDto> {
    override val provider: suspend () -> TrainingConfigDto = {
        val rawString = Res.readBytes("files/training_config.json").decodeToString()
        json.decodeFromString<TrainingConfigDto>(rawString)
    }
}
