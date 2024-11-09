package kovp.trainhard.home_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.home_presentation.HomeComposable

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeSection() {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> { HomeComposable() }
    }
}

fun NavController.navigateToHome(options: NavOptions) = navigate(HomeRoute, options)
