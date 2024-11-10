package kovp.trainhard.new_training_api

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kovp.trainhard.new_training_presentation.SelectExerciseTypeScreen
import kovp.trainhard.new_training_presentation.TrainingComposable
import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectNewExerciseTypeComposable

fun NavGraphBuilder.newTrainingFlow(navController: NavController) {
    composable<TrainingScreen> {
        TrainingComposable(
            currentTimestamp = it.toRoute<TrainingScreen>().timestamp,
            navController = navController,
        )
    }
    composable<SelectExerciseTypeScreen>(
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    ) {
        SelectNewExerciseTypeComposable(navController = navController)
    }
}
