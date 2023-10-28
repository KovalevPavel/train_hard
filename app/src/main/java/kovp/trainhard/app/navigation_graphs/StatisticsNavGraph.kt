package kovp.trainhard.app.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kovp.trainhard.navigation_api.BottomNavGraphRoutes
import kovp.trainhard.statistics_presentation.destinations.StatisticComposableDestination
import kovp.trainhard.statistics_presentation.destinations.StatisticsDetailsComposableDestination

object StatisticsNavGraph : NavGraphSpec {
    override val route: String = BottomNavGraphRoutes.STATISTICS.route
    override val startRoute: Route = StatisticComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        StatisticComposableDestination,
        StatisticsDetailsComposableDestination,
    )
        .associateBy(Direction::route)
}
