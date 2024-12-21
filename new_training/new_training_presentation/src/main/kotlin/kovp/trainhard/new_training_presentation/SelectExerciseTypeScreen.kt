package kovp.trainhard.new_training_presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ui.SelectNewExerciseTypeComposable

@Serializable
object SelectExerciseTypeScreen: ComposableScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable<SelectExerciseTypeScreen>(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            SelectNewExerciseTypeComposable(navController = navController)
        }
    }
}
