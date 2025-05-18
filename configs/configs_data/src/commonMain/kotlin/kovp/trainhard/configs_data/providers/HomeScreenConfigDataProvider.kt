package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.HomeScreenConfigDto
import trainhard.configs_data.generated.resources.Res

class HomeScreenConfigDataProvider(
    private val json: Json,
) : ConfigDataProvider<HomeScreenConfigDto> {
    override val provider: suspend () -> HomeScreenConfigDto = {
        val rawString = Res.readBytes("files/home_screen_config.json").decodeToString()
        json.decodeFromString<HomeScreenConfigDto>(rawString)
    }
}
