package kovp.trainhard.app.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import org.jetbrains.compose.resources.StringResource
import trainhard.app.generated.resources.Res
import trainhard.app.generated.resources.bottom_navigation_analytics
import trainhard.app.generated.resources.bottom_navigation_home
import trainhard.app.generated.resources.bottom_navigation_params
import kotlin.reflect.KClass

enum class BottomBarDestination(
    val icon: ImageVector,
    val label: StringResource,
    val baseRoute: KClass<*>,
) {
    Home(
        icon = Icons.Rounded.Home,
        label = Res.string.bottom_navigation_home,
        baseRoute = HomeBaseRoute::class,
    ),
    Statistics(
        icon = Icons.Rounded.BarChart,
        label = Res.string.bottom_navigation_analytics,
        baseRoute = StatisticsBaseRoute::class,
    ),
    Parameters(
        icon = Icons.AutoMirrored.Rounded.List,
        label = Res.string.bottom_navigation_params,
        baseRoute = ParametersBaseRoute::class,
    ),
}
