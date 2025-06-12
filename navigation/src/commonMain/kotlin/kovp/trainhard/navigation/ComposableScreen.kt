package kovp.trainhard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface ComposableScreen: Screen {
    fun createScreen(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
    )
}
