package kovp.trainhard.home_presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.home_presentation.gym_card_dates.GymCardDatesDialog
import kovp.trainhard.home_presentation.gym_card_dates.SelectGymDatesScreen

fun NavGraphBuilder.homeFlow(navController: NavController) {
    composable<HomeRoute> { HomeComposable(navController) }
    composable<SelectGymDatesScreen>(
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
