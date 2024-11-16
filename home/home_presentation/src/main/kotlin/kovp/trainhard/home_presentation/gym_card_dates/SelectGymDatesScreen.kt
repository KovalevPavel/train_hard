package kovp.trainhard.home_presentation.gym_card_dates

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.navigation.ComposableScreen

@Serializable
data class SelectGymDatesScreen(val startDate: Long?, val endDate: Long?) {
    companion object: ComposableScreen {
        const val DATE_RANGE_KEY = "DATE_RANGE_KEY"

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.composable<SelectGymDatesScreen>(
                enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up) },
                exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down) },
            ) { backStackEntry ->
                GymCardDatesDialog(
                    initDateRangeState = backStackEntry.toRoute<SelectGymDatesScreen>().let {
                        DateRangeSelectorState(startTimestamp = it.startDate, endTimestamp = it.endDate)
                    },
                    navController = navController,
                )
            }
        }
    }
}
