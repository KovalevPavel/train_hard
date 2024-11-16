package kovp.trainhard.home_presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen

@Serializable
data object HomeRoute: ComposableScreen {
    override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable<HomeRoute> { HomeComposable(navController) }
    }
}