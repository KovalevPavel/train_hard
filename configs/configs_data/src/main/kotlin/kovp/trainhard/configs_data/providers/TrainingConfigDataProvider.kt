package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.R
import kovp.trainhard.configs_data.TrainingConfigDto
import trainhard.kovp.core.ResourceProvider

class TrainingConfigDataProvider(
    private val resourceProvider: ResourceProvider,
    private val json: Json,
): ConfigDataProvider<TrainingConfigDto> {
    override val provider: () -> TrainingConfigDto = {
        val rawString = resourceProvider.getConfig(R.raw.training_config)
        json.decodeFromString<TrainingConfigDto>(rawString)
    }
}
