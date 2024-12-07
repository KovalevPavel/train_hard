package kovp.trainhard.parameters_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.parameters_presentation.parameters.ui.ParametersComposable

@Serializable
data object ParametersRoute : ComposableScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable<ParametersRoute> {
            ParametersComposable(navController = navController)
        }
    }
}
