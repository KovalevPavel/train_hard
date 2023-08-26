package me.kovp.trainhard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import me.kovp.trainhard.bottom_navigation.BottomBar
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.navigation_graphs.RootNavigationGraphSpec
import me.kovp.trainhard.ui_theme.TrainHardTheme
import me.kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = koinViewModel<MainActivityViewModelImpl>()

            //TODO: добавить скрытие сплеш-скрина после инициализации БД

            val screenMapper = remember { appScreenMapper }
            val systemUiController = rememberSystemUiController()

            val navController = rememberAnimatedNavController()
            val engine = rememberAnimatedNavHostEngine()

            CompositionLocalProvider(
                localScreenMapper provides screenMapper
            ) {
                TrainHardTheme {
                    systemUiController.setStatusBarColor(themeColors.black)

                    Scaffold(
                        bottomBar = {
                            BottomBar(navController = navController)
                        }
                    ) { paddingValues ->

                        DestinationsNavHost(
                            navController = navController,
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            navGraph = RootNavigationGraphSpec,
                            engine = engine,
                        )
                    }
                }
            }
        }
    }
}
