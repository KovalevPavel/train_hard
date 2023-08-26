package me.kovp.trainhard.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import me.kovp.trainhard.home_presentation.destinations.HomeComposableDestination
import me.kovp.trainhard.new_training_presentation.destinations.NewTrainingComposableDestination

object HomeNavGraph : NavGraphSpec {
    override val route: String = "home"
    override val startRoute: Route = HomeComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        HomeComposableDestination,
        NewTrainingComposableDestination,
    )
        .associateBy(Direction::route)
}
