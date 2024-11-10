package kovp.trainhard.home_presentation.gym_card_dates

import kotlinx.serialization.Serializable

@Serializable
data class SelectGymDatesScreen(val startDate: Long?, val endDate: Long?) {
    companion object {
        const val DATE_RANGE_KEY = "DATE_RANGE_KEY"
    }
}
