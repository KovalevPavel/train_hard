package kovp.trainhard.home_presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kovp.trainhard.home_presentation.gym_card_dates.SelectGymDatesScreen

fun NavGraphBuilder.homeFlow(navController: NavController) {
    HomeRoute.createScreen(navGraphBuilder = this, navController = navController)
    SelectGymDatesScreen.createScreen(navGraphBuilder = this, navController = navController)
}
