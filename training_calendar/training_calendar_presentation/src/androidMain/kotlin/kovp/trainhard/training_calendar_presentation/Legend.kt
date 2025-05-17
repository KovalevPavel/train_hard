package kovp.trainhard.training_calendar_presentation

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.toImmutableList
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.training_calendar_presentation.legend.Legend
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
