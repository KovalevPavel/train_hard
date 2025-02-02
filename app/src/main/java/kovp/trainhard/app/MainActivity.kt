package kovp.trainhard.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kovp.trainhard.app.bottom_navigation.BottomBar
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val myStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }

    private val splash: SplashScreen by lazy { installSplashScreen() }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        splash
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = koinViewModel()
            splash.setKeepOnScreenCondition { !viewModel.dbIsInitialized }

            val systemUiController = rememberSystemUiController()
            val navController = rememberNavController()

            CompositionLocalProvider(
                LocalViewModelStoreOwner provides myStoreOwner,
            ) {
                TrainHardTheme {
                    val backgroundColor = themeColors.black

                    DisposableEffect(key1 = systemUiController) {
                        systemUiController.setStatusBarColor(backgroundColor)

                        onDispose { }
                    }

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
    }
}
