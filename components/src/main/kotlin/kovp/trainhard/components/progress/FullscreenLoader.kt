package kovp.trainhard.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun FullscreenLoader() {
    Box(
        modifier = Modifier
            .background(color = themeColors.black)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        TrainProgressIndicator()
    }
}
