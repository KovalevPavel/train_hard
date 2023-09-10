package me.kovp.trainhard.statistics_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.trainhard.statistics_presentation.destinations.StatisticsDetailsComposableDestination
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Destination
@Composable
fun StatisticComposable(
    navigator: DestinationsNavigator,
) {
    Box(
        modifier = Modifier.background(color = themeColors.black),
        contentAlignment = Alignment.Center,
        ) {
        Text(
            text = "Statistics screen",
            style = themeTypography.body1,
            modifier = Modifier.clickable {
                navigator.navigate(StatisticsDetailsComposableDestination)
            },
        )
    }
}
