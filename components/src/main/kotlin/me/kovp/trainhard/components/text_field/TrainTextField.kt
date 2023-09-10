package me.kovp.trainhard.components.text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun TrainTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    onValueChanged: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(themeColors.white, shape = RoundedCornerShape(4.dp)),
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = value,
            onValueChange = { onValueChanged(it) },
            textStyle = themeTypography.body1.copy(themeColors.black),
            singleLine = true,
            cursorBrush = SolidColor(themeColors.lime),
        ) {
            Box {
                it()
                if (value.isEmpty() && hint.isNotEmpty()) {
                    Text(
                        text = hint,
                        style = themeTypography.body1
                            .copy(
                                color = themeColors.gray.copy(alpha = 0.5f),
                            ),
                    )
                }
            }
        }
    }
}
