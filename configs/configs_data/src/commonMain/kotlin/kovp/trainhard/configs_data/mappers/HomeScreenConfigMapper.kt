package kovp.trainhard.configs_data.mappers

import kovp.trainhard.configs_core.HomeScreenConfig
import kovp.trainhard.configs_data.HomeScreenConfigDto

class HomeScreenConfigMapper {
    fun mapConfig(dto: HomeScreenConfigDto): HomeScreenConfig {
        return HomeScreenConfig(
            gymCard = dto.gymCard.orEmpty(),
            gymCardStub = dto.gymCardStub.orEmpty(),
            activeProgramTitle = dto.activeProgramTitle.orEmpty(),
            defaultProgram = dto.defaultProgram.orEmpty(),
            todayPlan = dto.todayPlan.orEmpty(),
            muscleGroups = dto.muscleGroups.orEmpty(),
            startTrainingAction = dto.startTrainingAction.orEmpty(),
            todayPlanRestDay = dto.todayPlanRestDay.orEmpty(),
            todayPlanNotSelected = dto.todayPlanNotSelected.orEmpty(),
        )
    }
}
