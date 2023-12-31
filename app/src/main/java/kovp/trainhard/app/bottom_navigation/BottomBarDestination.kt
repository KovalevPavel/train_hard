package kovp.trainhard.app.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.NavGraphSpec
import kovp.trainhard.app.R
import kovp.trainhard.app.navigation_graphs.HomeNavGraph
import kovp.trainhard.app.navigation_graphs.ParametersNavGraph
import kovp.trainhard.app.navigation_graphs.StatisticsNavGraph

enum class BottomBarDestination(
    val direction: NavGraphSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    Home(
        direction = HomeNavGraph,
        icon = R.drawable.icon_home,
        label = R.string.bottom_navigation_home,
    ),
    Statistics(
        direction = StatisticsNavGraph,
        icon = R.drawable.statistics_icon,
        label = R.string.bottom_navigation_analytics,
    ),
    Parameters(
        direction = ParametersNavGraph,
        icon = R.drawable.list_icon,
        label = R.string.bottom_navigation_params,
    )
}
