package me.kovp.trainhard.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import me.kovp.trainhard.home_presentation.destinations.HomeComposableDestination
import me.kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import me.kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import me.kovp.trainhard.new_training_presentation.destinations.TrainingComposableDestination

object HomeNavGraph : NavGraphSpec {
    override val route: String = "home"
    override val startRoute: Route = HomeComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        HomeComposableDestination,
        TrainingComposableDestination,
        SelectNewExerciseTypeComposableDestination,
        NewSetDialogDestination,
    )
        .associateBy { it.route }
}
