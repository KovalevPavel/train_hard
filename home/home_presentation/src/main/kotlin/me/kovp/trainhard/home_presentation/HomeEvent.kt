package me.kovp.trainhard.home_presentation

sealed interface HomeEvent {
    data object OnStartTrainingClick : HomeEvent
    data object OnGymCardPlateClick : HomeEvent
    data class EditGymCardDates(val startTimestamp: Long, val endTimestamp: Long) : HomeEvent
}
