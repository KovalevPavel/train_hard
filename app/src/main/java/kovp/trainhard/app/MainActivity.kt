package kovp.trainhard.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kovp.trainhard.app.bottom_navigation.BottomBarDestination
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.home_api.homeSection
import kovp.trainhard.home_api.navigateToHome
import kovp.trainhard.parameters_api.navigateToParams
import kovp.trainhard.parameters_api.parametersSection
import kovp.trainhard.statistics_api.navigateToStatistics
import kovp.trainhard.statistics_api.statisticsSection
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val myStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = koinViewModel()

            //TODO: добавить скрытие сплеш-скрина после инициализации БД

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

                    Scaffold(
                        containerColor = backgroundColor,
                        bottomBar = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(themeColors.gray),
                                )

                                NavigationBar(
                                    containerColor = themeColors.black,
                                ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentDestination = navBackStackEntry?.destination

                                    BottomBarDestination.entries.forEach { topLevelRoute ->
                                        NavigationBarItem(
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = themeColors.white,
                                                selectedTextColor = themeColors.white,
                                                indicatorColor = themeColors.black,
                                                unselectedIconColor = themeColors.gray,
                                                disabledIconColor = themeColors.gray,
                                            ),
                                            selected = currentDestination?.hierarchy?.any {
                                                it.hasRoute(
                                                    topLevelRoute.baseRoute
                                                )
                                            } == true,
                                            icon = {
                                                Icon(
                                                    painter = painterResource(id = topLevelRoute.icon),
                                                    contentDescription = null
                                                )
                                            },
                                            label = { Text(text = stringResource(id = topLevelRoute.label)) },
                                            alwaysShowLabel = false,
                                            onClick = {
                                                val options = navOptions {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }

                                                when (topLevelRoute) {
                                                    BottomBarDestination.Home -> navController.navigateToHome(
                                                        options
                                                    )

                                                    BottomBarDestination.Statistics -> navController.navigateToStatistics(
                                                        options
                                                    )

                                                    BottomBarDestination.Parameters -> navController.navigateToParams(
                                                        options
                                                    )
                                                }
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    ) { paddingValues ->
                        NavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            startDestination = HomeBaseRoute,
                            enterTransition = { scaleIn(initialScale = 1f) },
                            exitTransition = { scaleOut(targetScale = 1f) },
                        ) {
                            homeSection(navController = navController)
                            statisticsSection(navController = navController)
                            parametersSection(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
