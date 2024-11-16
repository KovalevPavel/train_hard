package kovp.trainhard.home_api

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.home_presentation.HomeRoute
import kovp.trainhard.home_presentation.homeFlow
import kovp.trainhard.navigation.HostScreen
import kovp.trainhard.new_training_api.newTrainingFlow
import kovp.trainhard.training_calendar_api.TrainingCalendarScreen

@Serializable
data object HomeBaseRoute: HostScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<HomeBaseRoute>(
            startDestination = HomeRoute,
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
            homeFlow(navController)
            newTrainingFlow(navController)
            TrainingCalendarScreen.createScreen(navGraphBuilder = this, navController = navController)
        }
    }

    override fun navigateHome(navController: NavController, options: NavOptions) {
        navController.navigate(HomeRoute, options)
    }
}
