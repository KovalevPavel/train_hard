package me.kovp.trainhard.components.fab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun TrainFab(
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.size(56.dp),
        shape = AbsoluteRoundedCornerShape(28.dp),
        color = themeColors.lime,
        onClick = onClick
    ) {
        Image(imageVector = icon, contentDescription = null)
    }
}
