package me.kovp.trainhard.statistics_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun StatisticsDetailsComposable() {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "Statistics details screen",
        )
    }
}
