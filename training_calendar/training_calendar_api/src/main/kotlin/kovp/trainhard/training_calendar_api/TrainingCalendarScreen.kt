package kovp.trainhard.training_calendar_api

import kotlinx.serialization.Serializable

@Serializable
data class TrainingCalendarScreen(val lastAvailableDate: Long)
