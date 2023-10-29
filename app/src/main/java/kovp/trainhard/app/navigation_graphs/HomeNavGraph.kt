package kovp.trainhard.app.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kovp.trainhard.home_presentation.destinations.GymCardDatesDialogDestination
import kovp.trainhard.home_presentation.destinations.HomeComposableDestination
import kovp.trainhard.navigation_api.BottomNavGraphRoutes
import kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import kovp.trainhard.new_training_presentation.destinations.TrainingComposableDestination
import kovp.trainhard.training_calendar_presentation.destinations.TrainingCalendarDestination

object HomeNavGraph : NavGraphSpec {
    override val route: String = BottomNavGraphRoutes.HOME.route
    override val startRoute: Route = HomeComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        HomeComposableDestination,
        TrainingComposableDestination,
        SelectNewExerciseTypeComposableDestination,
        NewSetDialogDestination,
        TrainingCalendarDestination,
        GymCardDatesDialogDestination,
    )
        .associateBy { it.route }
}
