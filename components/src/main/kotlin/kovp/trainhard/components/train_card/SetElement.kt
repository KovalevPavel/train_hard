package kovp.trainhard.components.train_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import kovp.trainhard.core_design.conditional
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun SetElement(
    text: String,
    isEditable: Boolean,
    onRemoveClick: () -> Unit = {},
    onSetClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            /*
            Порядок применения параметров не изменять и не объединять:
            пострадает визуальная составляющая
             */
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .conditional(
                condition = isEditable,
                ifTrue = { clickable { onSetClick() } },
            )
            .height(60.dp)
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = text, style = themeTypography.body2)

        if (isEditable) {
            IconButton(onClick = { onRemoveClick() }) {
                Image(
                    imageVector = Filled.Clear,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = themeColors.white),
                )
            }
        }
    }
}
