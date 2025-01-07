package kovp.trainhard.new_training_api

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.new_training_presentation.screen.ui.TrainingComposable

@Serializable
data class TrainingScreen(private val timestamp: Long) {
    companion object : ComposableScreen {
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.composable<TrainingScreen>(
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
                },
                exitTransition = {
                    scaleOut(targetScale = 1f)
                },
                popEnterTransition = {
                    scaleIn(initialScale = 1f)
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
                },
            ) {
                TrainingComposable(
                    currentTimestamp = it.toRoute<TrainingScreen>().timestamp,
                    navController = navController,
                )
            }
        }
    }
}
