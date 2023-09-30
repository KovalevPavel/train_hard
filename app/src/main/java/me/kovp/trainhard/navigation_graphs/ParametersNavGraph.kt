package me.kovp.trainhard.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import me.kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import me.kovp.trainhard.parameters_presentation.destinations.ParametersComposableDestination
import trainhard.core_dialogs.destinations.AlertConfirmationDialogDestination

object ParametersNavGraph : NavGraphSpec {
    override val route: String = "parameters"
    override val startRoute: Route = ParametersComposableDestination

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf(
        ParametersComposableDestination,
        NewExerciseScreenDestination,
        AlertConfirmationDialogDestination,
    )
        .associateBy { it.route }
}
