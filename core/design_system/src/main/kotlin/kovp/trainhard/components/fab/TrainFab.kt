package kovp.trainhard.components.fab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun TrainFab(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = themeColors.lime,
        onClick = onClick
    ) {
        Image(modifier = Modifier.padding(16.dp), imageVector = icon, contentDescription = null)
    }
}

@Preview
@Composable
private fun TrainFabPreview() {
    TrainHardTheme {
        TrainFab(icon = Icons.Filled.Add) { }
    }
}
