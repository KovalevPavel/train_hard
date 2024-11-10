package kovp.trainhard.statistics_api

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.statistics_presentation.StatisticComposable
import kovp.trainhard.statistics_presentation.StatisticsDetailsComposable
import kovp.trainhard.statistics_presentation.StatisticsDetailsRoute

@Serializable
data object StatisticsBaseRoute

@Serializable
data object StatisticsRoute

fun NavGraphBuilder.statisticsSection(
    navController: NavController,
) {
    navigation<StatisticsBaseRoute>(
        startDestination = StatisticsRoute,
    ) {
        composable<StatisticsRoute> {
            StatisticComposable(navController = navController)
        }
        composable<StatisticsDetailsRoute>(
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            StatisticsDetailsComposable()
        }
    }
}

fun NavController.navigateToStatistics(options: NavOptions) = navigate(StatisticsRoute, options)
