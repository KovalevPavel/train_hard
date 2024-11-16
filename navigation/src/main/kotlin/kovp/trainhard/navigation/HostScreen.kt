package kovp.trainhard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

interface HostScreen: ComposableScreen {
    fun navigateHome(navController: NavController, options: NavOptions)
}
