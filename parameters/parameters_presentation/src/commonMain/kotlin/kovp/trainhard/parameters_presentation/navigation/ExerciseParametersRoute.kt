package kovp.trainhard.parameters_presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.parameters_presentation.exercise_parameters.ui.ExerciseParametersComposable

@Serializable
data class ExerciseParametersRoute(
    val title: String,
    val muscleIds: List<String>,
) {
    companion object : ComposableScreen {
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.composable<ExerciseParametersRoute>(
                enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            ) { backstackEntry ->
                ExerciseParametersComposable(
                    argument = backstackEntry.toRoute<ExerciseParametersRoute>().let {
                        ExerciseParametersArg(
                            title = it.title,
                            muscleIds = it.muscleIds,
                        )
                    },
                    navController = navController,
                )
            }
        }
    }
}
