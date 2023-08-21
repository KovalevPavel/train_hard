package me.kovp.trainhard.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.NavGraphSpec
import me.kovp.trainhard.R
import me.kovp.trainhard.R.string
import me.kovp.trainhard.home_presentation.HomeNavGraph
import me.kovp.trainhard.parameters_presentation.ParametersNavGraph
import me.kovp.trainhard.statistics_presentation.StatisticsNavGraph

enum class BottomBarDestination(
    val direction: NavGraphSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    Home(
        direction = HomeNavGraph,
        icon = R.drawable.icon_home,
        label = string.bottom_navigation_home,
    ),
    Statistics(
        direction = StatisticsNavGraph,
        icon = R.drawable.statistics_icon,
        label = string.bottom_navigation_analytics,
    ),
    Parameters(
        direction = ParametersNavGraph,
        icon = R.drawable.list_icon,
        label = string.bottom_navigation_params,
    )
}