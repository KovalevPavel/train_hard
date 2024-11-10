package kovp.trainhard.app.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kovp.trainhard.app.R
import kovp.trainhard.home_api.HomeBaseRoute
import kovp.trainhard.parameters_api.ParametersBaseRoute
import kovp.trainhard.statistics_api.StatisticsBaseRoute
import kotlin.reflect.KClass

enum class BottomBarDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val baseRoute: KClass<*>,
) {
    Home(
        icon = R.drawable.icon_home,
        label = R.string.bottom_navigation_home,
        baseRoute = HomeBaseRoute::class,
    ),
    Statistics(
        icon = R.drawable.statistics_icon,
        label = R.string.bottom_navigation_analytics,
        baseRoute = StatisticsBaseRoute::class,
    ),
    Parameters(
        icon = R.drawable.list_icon,
        label = R.string.bottom_navigation_params,
        baseRoute = ParametersBaseRoute::class,
    ),
}
