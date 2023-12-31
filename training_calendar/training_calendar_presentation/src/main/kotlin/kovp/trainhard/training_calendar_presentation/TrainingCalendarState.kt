package kovp.trainhard.training_calendar_presentation

import kovp.trainhard.core_domain.MuscleGroup
import java.time.LocalDate

sealed interface TrainingCalendarState {
    data object Loading : TrainingCalendarState
    data class Data(
        val trainings: Map<LocalDate, List<MuscleGroup>>,
    ) : TrainingCalendarState
}
