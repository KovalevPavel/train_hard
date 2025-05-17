package kovp.trainhard.components.selectors

import kotlinx.serialization.Serializable

@Serializable
data class DateRangeSelectorState(
    val startTimestamp: Long?,
    val endTimestamp: Long?,
)
