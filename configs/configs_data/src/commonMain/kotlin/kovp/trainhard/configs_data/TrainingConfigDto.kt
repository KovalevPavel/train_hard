package kovp.trainhard.configs_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TrainingConfigDto(
    @SerialName("weightIncrement")
    val weightIncrement: Float?,
    @SerialName("firstMonthOffset")
    val firstMonthOffset: Int?,
    @SerialName("startYear")
    val startYear: Int?,
)
