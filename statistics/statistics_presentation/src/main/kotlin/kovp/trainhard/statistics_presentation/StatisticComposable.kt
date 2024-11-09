package kovp.trainhard.statistics_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun StatisticComposable(onTextClick: () -> Unit) {
    Box(
        modifier = Modifier.background(color = themeColors.black).fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.clickable { onTextClick() },
            text = "Statistics screen",
            style = themeTypography.body1,
        )
    }
}
