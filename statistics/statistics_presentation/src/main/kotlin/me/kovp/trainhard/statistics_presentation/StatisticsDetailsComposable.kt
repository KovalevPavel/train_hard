package me.kovp.trainhard.statistics_presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun StatisticsDetailsComposable() {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "Statistics details screen",
        )
    }
}
