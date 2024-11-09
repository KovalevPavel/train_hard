package kovp.trainhard.statistics_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.statistics_presentation.StatisticComposable

@Serializable
data object StatisticsBaseRoute

@Serializable
data object StatisticsRoute

fun NavGraphBuilder.statisticsSection() {
    navigation<StatisticsBaseRoute>(startDestination = StatisticsRoute) {
        composable<StatisticsRoute> {
            StatisticComposable()
        }
    }
}

fun NavController.navigateToStatistics(options: NavOptions) = navigate(StatisticsRoute, options)
