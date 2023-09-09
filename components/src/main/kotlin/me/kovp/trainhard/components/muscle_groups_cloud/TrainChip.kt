package me.kovp.trainhard.components.muscle_groups_cloud

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import me.kovp.trainhard.core_design.mapMuscleTitle
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainChip(
    muscle: Muscle,
    onChipClick: (muscle: Muscle, isChecked: Boolean) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }

    FilterChip(
        shape = RoundedCornerShape(24.dp),
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = themeColors.lime,
        ),
        onClick = {
            isSelected = !isSelected
            onChipClick(muscle, isSelected)
        },
        label = {
            val string =
                mapMuscleTitle(muscleId = muscle.id).replaceFirstChar { it.uppercaseChar() }
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = string,
                style = themeTypography.body2.copy(
                    color = if (isSelected) themeColors.black else themeColors.white,
                )
            )
        },
    )
}
