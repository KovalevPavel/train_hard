package kovp.trainhard.app.bottom_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalDivider()

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
                        it.hasRoute(topLevelRoute.baseRoute)
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
                            BottomBarDestination.Home -> {
                                HomeBaseRoute.navigateHome(navController, options)
                            }

                            BottomBarDestination.Statistics -> {
                                StatisticsBaseRoute.navigateHome(navController, options)
                            }

                            BottomBarDestination.Parameters -> {
                                ParametersBaseRoute.navigateHome(navController, options)
                            }
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    TrainHardTheme {
        BottomBar(navController = rememberNavController())
    }
}
