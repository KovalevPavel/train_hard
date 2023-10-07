package me.kovp.trainhard.home_presentation

sealed interface HomeScreenState {
    data class Data(
        val dateString: Long,
        val gymHealth: Float,
        val todayPlan: TodayPlan,
        val isLoading: Boolean,
    ) : HomeScreenState

    data object Loading : HomeScreenState
}
