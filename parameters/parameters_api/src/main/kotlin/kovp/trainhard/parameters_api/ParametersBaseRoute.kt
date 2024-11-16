package kovp.trainhard.parameters_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.HostScreen
import kovp.trainhard.parameters_presentation.ParametersComposable
import kovp.trainhard.parameters_presentation.ParametersRoute

@Serializable
data object ParametersBaseRoute : HostScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<ParametersBaseRoute>(startDestination = ParametersRoute) {
            composable<ParametersRoute> {
                ParametersComposable(navController = navController)
            }
        }
    }

    override fun navigateHome(navController: NavController, options: NavOptions) {
        navController.navigate(ParametersRoute, options)
    }
}
