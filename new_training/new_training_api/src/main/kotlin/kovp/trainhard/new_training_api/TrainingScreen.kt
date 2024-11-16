package kovp.trainhard.new_training_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.new_training_presentation.TrainingComposable

@Serializable
data class TrainingScreen(private val timestamp: Long) {
    companion object : ComposableScreen {
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.composable<TrainingScreen> {
                TrainingComposable(
                    currentTimestamp = it.toRoute<TrainingScreen>().timestamp,
                    navController = navController,
                )
            }
        }
    }
}
