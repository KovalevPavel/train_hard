package kovp.trainhard.app.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kovp.trainhard.navigation_api.BottomNavGraphRoutes
import kovp.trainhard.parameters_presentation.destinations.AlertConfirmationDialogDestination
import kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import kovp.trainhard.parameters_presentation.destinations.ParametersComposableDestination

object ParametersNavGraph : NavGraphSpec {
    override val route: String = BottomNavGraphRoutes.PARAMETERS.route
    override val startRoute: Route = ParametersComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        ParametersComposableDestination,
        NewExerciseScreenDestination,
        AlertConfirmationDialogDestination,
    )
        .associateBy { it.route }
}
