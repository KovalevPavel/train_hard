package me.kovp.trainhard.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun TrainButton(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    TrainButton(modifier = modifier, onClick = onClick, isEnabled = isEnabled) {
        Text(
            text = label,
            style = themeTypography.body2.copy(color = themeColors.black),
        )
    }
}

@Composable
fun TrainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = themeColors.lime,
            contentColor = themeColors.black,
            disabledContainerColor = themeColors.gray,
        ),
    ) {
        content()
    }
}
