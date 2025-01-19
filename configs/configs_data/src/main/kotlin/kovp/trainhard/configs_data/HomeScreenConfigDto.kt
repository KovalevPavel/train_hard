package kovp.trainhard.configs_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class HomeScreenConfigDto(
    @SerialName("gymCard")
    val gymCard: String?,
    @SerialName("gymCardStub")
    val gymCardStub: String?,
    @SerialName("activeProgramTitle")
    val activeProgramTitle: String?,
    @SerialName("defaultProgram")
    val defaultProgram: String?,
    @SerialName("todayPlan")
    val todayPlan: String?,
    @SerialName("muscleGroups")
    val muscleGroups: String?,
    @SerialName("startTrainingAction")
    val startTrainingAction: String?,
    @SerialName("todayPlanRestDay")
    val todayPlanRestDay: String?,
    @SerialName("todayPlanNotSelected")
    val todayPlanNotSelected: String?,
)
