package kovp.trainhard.home_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.homeFlow(navController: NavController) {
    HomeRoute.createScreen(navGraphBuilder = this, navController = navController)
    SelectGymDatesScreen.createScreen(navGraphBuilder = this, navController = navController)
}
