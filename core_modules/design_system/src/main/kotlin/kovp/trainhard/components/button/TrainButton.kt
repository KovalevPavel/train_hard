package kovp.trainhard.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun TrainButton(
    modifier: Modifier = Modifier,
    label: String,
    isPrimary: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    TrainButton(
        modifier = modifier.sizeIn(minWidth = 100.dp, minHeight = 50.dp),
        onClick = onClick,
        isEnabled = isEnabled,
        isPrimary = isPrimary,
    ) {
        Text(
            text = label,
            style = themeTypography.body2.copy(
                color = if (isPrimary) themeColors.black else themeColors.lime,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TrainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean,
    isPrimary: Boolean,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        border = isPrimary.takeUnless { it }?.let {
            BorderStroke(
                width = 1.dp,
                color = if (isEnabled) themeColors.lime else themeColors.black,
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) themeColors.lime else themeColors.gray,
            contentColor = if (isPrimary) themeColors.black else themeColors.lime,
            disabledContainerColor = if (isPrimary) {
                themeColors.lime.copy(alpha = 0.5f)
            } else {
                themeColors.gray
            },
        ),
    ) {
        content()
    }
}
