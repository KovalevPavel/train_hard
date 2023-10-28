package kovp.trainhard.app.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import kovp.trainhard.app.CurrentHostScreenFlowHolder
import kovp.trainhard.app.navigation_graphs.RootNavigationGraphSpec
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.compose.koinInject

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentScreenHolder = koinInject<CurrentHostScreenFlowHolder>()
    val currentGraph by currentScreenHolder.currentGraphFlow.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(themeColors.gray),
        )

        NavigationBar(
            modifier = modifier,
            containerColor = themeColors.black,
        ) {
            BottomBarDestination.values().forEach {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = themeColors.white,
                        selectedTextColor = themeColors.white,
                        indicatorColor = themeColors.black,
                        unselectedIconColor = themeColors.gray,
                        disabledIconColor = themeColors.gray,
                    ),
                    selected = currentGraph.route.equals(it.direction.route, ignoreCase = true),
                    icon = {
                        Icon(painter = painterResource(id = it.icon), contentDescription = null)
                    },
                    label = { Text(text = stringResource(id = it.label)) },
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(it.direction) {
                            popUpTo(currentGraph.route) {
                                saveState = true
                                inclusive = true
                            }
                            restoreState = true
                            RootNavigationGraphSpec.updateSelectedGraph(newGraph = it.direction)
                        }
                    },
                )
            }
        }
    }
}
