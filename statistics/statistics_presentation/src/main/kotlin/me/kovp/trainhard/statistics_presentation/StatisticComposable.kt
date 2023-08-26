package me.kovp.trainhard.statistics_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.trainhard.statistics_presentation.destinations.StatisticsDetailsComposableDestination

@Destination
@Composable
fun StatisticComposable(
    navigator: DestinationsNavigator,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "Statistics screen",
            modifier = Modifier.clickable {
                navigator.navigate(StatisticsDetailsComposableDestination)
            },
        )
    }
}
