package kovp.trainhard.training_calendar_presentation

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kovp.trainhard.core_domain.MuscleGroup
import java.time.LocalDate

@Immutable
sealed interface TrainingCalendarState {
    data object Loading : TrainingCalendarState
    data class Data(
        val muscleGroups: ImmutableList<LegendMuscleGroupVs>,
        val trainings: ImmutableMap<LocalDate, List<MuscleGroup>>,
    ) : TrainingCalendarState

    data class LegendMuscleGroupVs(
        val group: MuscleGroup,
        val title: String,
    )
}
