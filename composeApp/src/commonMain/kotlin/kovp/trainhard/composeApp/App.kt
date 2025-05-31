package kovp.trainhard.composeApp

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kovp.trainhard.composeApp.bottom_navigation.BottomBar
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
expect fun SetStatusBar()

@Composable
fun App() {
    val myStoreOwner = remember {
        object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = ViewModelStore()
        }
    }

    TrainHardTheme {
        val navController = rememberNavController()

        CompositionLocalProvider(
            LocalViewModelStoreOwner provides myStoreOwner,
        ) {
            SetStatusBar()

            Column(
                modifier = Modifier
                    .background(themeColors.black)
                    .fillMaxSize(),
            ) {
                NavHost(
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    startDestination = HomeBaseRoute,
                    enterTransition = { scaleIn(initialScale = 1f) },
                    exitTransition = { scaleOut(targetScale = 1f) },
                ) {
                    HomeBaseRoute.createScreen(
                        navGraphBuilder = this,
                        navController = navController,
                    )
                    StatisticsBaseRoute.createScreen(
                        navGraphBuilder = this,
                        navController = navController,
                    )
                    ParametersBaseRoute.createScreen(
                        navGraphBuilder = this,
                        navController = navController,
                    )
                }
                BottomBar(navController = navController)
            }
        }
    }
}
