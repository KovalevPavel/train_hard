package kovp.trainhard.training_calendar_presentation.legend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.muscle_groups_cloud.getMuscleGroupTitle
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.training_calendar_presentation.MuscleDot
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Legend(
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MuscleGroup.values().forEach { MuscleGroupLegendElement(it) }
    }
}

@Composable
private fun MuscleGroupLegendElement(muscleGroup: MuscleGroup) {
    val title = muscleGroup.getMuscleGroupTitle()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MuscleDot(
            modifier = Modifier.size(16.dp),
            muscleGroup = muscleGroup,
        )

        Text(
            text = title,
            style = themeTypography.body3,
        )
    }
}
