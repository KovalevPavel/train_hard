package kovp.trainhard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun GlowContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .dropShadow(
                shape = RoundedCornerShape(16.dp),
                color = themeColors.lime,
                offsetY = 0.dp,
                blur = 10.dp
            ),
        shape = RoundedCornerShape(16.dp),
    ) {
        content()
    }
}

@Preview
@Composable
private fun GlowContainerPreview() {
    TrainHardTheme {
        GlowContainer { Box(modifier = Modifier.size(60.dp)) }
    }
}
