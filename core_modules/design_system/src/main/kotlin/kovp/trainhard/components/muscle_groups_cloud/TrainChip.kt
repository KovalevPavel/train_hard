package kovp.trainhard.components.muscle_groups_cloud

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun TrainChip(
    id: String,
    title: String,
    selected: Boolean,
    onChipClick: (muscleId: String, isChecked: Boolean) -> Unit,
) {
    var isSelected by remember { mutableStateOf(selected) }

    FilterChip(
        shape = RoundedCornerShape(24.dp),
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = themeColors.lime,
        ),
        onClick = {
            isSelected = !isSelected
            onChipClick(id, isSelected)
        },
        label = {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = title,
                style = themeTypography.body2.copy(
                    color = if (isSelected) themeColors.black else themeColors.white,
                )
            )
        },
    )
}
