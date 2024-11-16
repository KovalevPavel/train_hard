package kovp.trainhard.new_training_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kovp.trainhard.new_training_presentation.SelectExerciseTypeScreen

fun NavGraphBuilder.newTrainingFlow(navController: NavController) {
    TrainingScreen.createScreen(navGraphBuilder = this, navController = navController)
    SelectExerciseTypeScreen.createScreen(navGraphBuilder = this, navController = navController)
}
