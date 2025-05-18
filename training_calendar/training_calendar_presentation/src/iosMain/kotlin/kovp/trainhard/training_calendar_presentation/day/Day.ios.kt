package kovp.trainhard.training_calendar_presentation.day

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import kovp.trainhard.core_domain.MuscleGroup

@Composable
internal actual fun Day(
    groups: List<MuscleGroup>,
    day: CalendarDay,
    onClick: (LocalDate) -> Unit
) {
}