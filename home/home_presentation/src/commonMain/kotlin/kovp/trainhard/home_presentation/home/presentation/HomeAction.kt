package kovp.trainhard.home_presentation.home.presentation

sealed interface HomeAction {
    data object OnStartTrainingClick : HomeAction
    data object OnGymCardPlateClick : HomeAction
    data object OnCalendarClick: HomeAction
    data class EditGymCardDates(val startTimestamp: Long, val endTimestamp: Long) : HomeAction
}
