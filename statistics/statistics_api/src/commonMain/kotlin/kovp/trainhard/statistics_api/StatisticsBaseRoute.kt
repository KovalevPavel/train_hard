package kovp.trainhard.statistics_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.HostScreen
import kovp.trainhard.statistics_presentation.StatisticsDetailsRoute
import kovp.trainhard.statistics_presentation.StatisticsRoute

@Serializable
data object StatisticsBaseRoute: HostScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<StatisticsBaseRoute>(
            startDestination = StatisticsRoute,
        ) {
            StatisticsRoute.createScreen(navGraphBuilder = this, navController = navController)
            StatisticsDetailsRoute.createScreen(navGraphBuilder = this, navController = navController)
        }
    }

    override fun navigateHome(navController: NavController, options: NavOptions) {
        navController.navigate(StatisticsRoute, options)
    }
}
