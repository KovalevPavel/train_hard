package kovp.trainhard.new_training_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kovp.trainhard.new_training_presentation.TrainingComposable

fun NavGraphBuilder.newTrainingFlow(navController: NavController) {
    composable<TrainingScreen> {
        TrainingComposable(
            currentTimestamp = it.toRoute<TrainingScreen>().timestamp,
            navController = navController,
        )
    }
}
