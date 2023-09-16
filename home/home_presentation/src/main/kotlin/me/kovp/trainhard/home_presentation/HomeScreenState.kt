package me.kovp.trainhard.home_presentation

data class HomeScreenState(
    val dateString: Long,
    val gymHealth: Float,
    val todayPlan: TodayPlan,
    val isLoading: Boolean,
) {
    companion object {
        val init = HomeScreenState(
            dateString = 0,
            gymHealth = 0f,
            todayPlan = TodayPlan.NoProgramSelected,
            isLoading = true,
        )
    }
}
