package kovp.trainhard.home_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.home_presentation.HomeComposable
import kovp.trainhard.new_training_api.newTrainingFlow

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeSection(navController: NavController) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> { HomeComposable(navController) }
        newTrainingFlow(navController)
    }
}

fun NavController.navigateToHome(options: NavOptions) = navigate(HomeRoute, options)
