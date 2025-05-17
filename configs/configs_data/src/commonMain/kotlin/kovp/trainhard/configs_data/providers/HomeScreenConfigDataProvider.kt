package kovp.trainhard.configs_data.providers

import kotlinx.serialization.json.Json
import kovp.trainhard.configs_data.HomeScreenConfigDto
//import kovp.trainhard.configs_data.R
import kovp.trainhard.core.ResourceProvider

class HomeScreenConfigDataProvider(
    private val resourceProvider: ResourceProvider,
    private val json: Json,
) : ConfigDataProvider<HomeScreenConfigDto> {
    override val provider: suspend () -> HomeScreenConfigDto = {
//        val rawString = resourceProvider.getConfig(R.raw.home_screen_config)
//        json.decodeFromString<HomeScreenConfigDto>(rawString)
        HomeScreenConfigDto(
            gymCard = null,
            gymCardStub = null,
            activeProgramTitle = null,
            defaultProgram = null,
            todayPlan = null,
            muscleGroups = null,
            startTrainingAction = null,
            todayPlanRestDay = null,
            todayPlanNotSelected = null
        )
    }
}
