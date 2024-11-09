package kovp.trainhard.app.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kovp.trainhard.app.R
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.home_api.HomeRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.parameters_api.ParametersRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import kovp.trainhard.statistics_api.StatisticsRoute
import kotlin.reflect.KClass

enum class BottomBarDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val baseRoute: KClass<*>,
    val route: KClass<*>,
) {
    Home(
        icon = R.drawable.icon_home,
        label = R.string.bottom_navigation_home,
        baseRoute = HomeBaseRoute::class,
        route = HomeRoute::class
    ),
    Statistics(
        icon = R.drawable.statistics_icon,
        label = R.string.bottom_navigation_analytics,
        baseRoute = StatisticsBaseRoute::class,
        route = StatisticsRoute::class,
    ),
    Parameters(
        icon = R.drawable.list_icon,
        label = R.string.bottom_navigation_params,
        baseRoute = ParametersBaseRoute::class,
        route = ParametersRoute::class,
    ),
}
