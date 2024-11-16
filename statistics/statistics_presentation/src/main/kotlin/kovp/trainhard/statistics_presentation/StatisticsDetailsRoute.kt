package kovp.trainhard.statistics_presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen

@Serializable
data object StatisticsDetailsRoute : ComposableScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable<StatisticsDetailsRoute>(
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            StatisticsDetailsComposable()
        }
    }
}
