package kovp.trainhard.training_calendar_presentation.legend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.training_calendar_presentation.MuscleDot
import kovp.trainhard.training_calendar_presentation.TrainingCalendarState
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Legend(
    modifier: Modifier = Modifier,
    muscleGroups: ImmutableList<TrainingCalendarState.LegendMuscleGroupVs>,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        muscleGroups.forEach { MuscleGroupLegendElement(it) }
    }
}

@Composable
private fun RowScope.MuscleGroupLegendElement(muscleGroup: TrainingCalendarState.LegendMuscleGroupVs) {

    Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MuscleDot(
            modifier = Modifier.size(16.dp),
            muscleGroup = muscleGroup.group,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = muscleGroup.title,
            style = themeTypography.body3,
        )
    }
}

@Preview
@Composable
private fun LegendPreview() {
    TrainHardTheme {
        Legend(
            muscleGroups = MuscleGroup.entries.map {
                TrainingCalendarState.LegendMuscleGroupVs(
                    group = it,
                    title = it.toString(),
                )
            }
                .toImmutableList())
    }
}
