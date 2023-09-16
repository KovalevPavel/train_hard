package me.kovp.trainhard.components.progress

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import me.kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun TrainProgressIndicator(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = themeColors.lime,
        strokeCap = StrokeCap.Square,
    )
}
