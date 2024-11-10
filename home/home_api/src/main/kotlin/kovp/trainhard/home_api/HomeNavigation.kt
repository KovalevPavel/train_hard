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
import kovp.trainhard.new_training_api.newTrainingFlow
import kovp.trainhard.training_calendar_api.trainingCalendarFlow

@Serializable
data object HomeBaseRoute

fun NavGraphBuilder.homeSection(navController: NavController) {
    navigation<HomeBaseRoute>(
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
        trainingCalendarFlow(navController)
    }
}

fun NavController.navigateToHome(options: NavOptions) = navigate(HomeRoute, options)
