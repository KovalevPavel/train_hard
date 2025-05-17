package kovp.trainhard.training_calendar_presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import kovp.trainhard.core_domain.MuscleGroup

@Composable
actual fun CalendarData(
    modifier: Modifier,
    firstMonthOffset: Long,
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit
) {
    Text("not implemented yet")
}