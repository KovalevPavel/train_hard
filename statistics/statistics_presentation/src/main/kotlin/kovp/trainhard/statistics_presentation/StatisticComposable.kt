package kovp.trainhard.statistics_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun StatisticComposable() {
    Box(
        modifier = Modifier.background(color = themeColors.black),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Statistics screen",
            style = themeTypography.body1,
        )
    }
}
