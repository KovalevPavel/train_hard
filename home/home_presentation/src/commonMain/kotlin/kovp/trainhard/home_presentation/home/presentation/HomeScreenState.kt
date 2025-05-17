package kovp.trainhard.home_presentation.home.presentation

sealed interface HomeScreenState {
    data class Data(
        val dateString: Long,
        val gymHealth: GymCardDates,
        val todayPlan: TodayPlan,
    ) : HomeScreenState

    data class GymCardDates(val start: Long?, val end: Long?)

    data object Loading : HomeScreenState
}
