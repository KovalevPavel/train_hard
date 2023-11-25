package kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition.MonthDate
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kovp.trainhard.core_domain.DATE_FORMAT_LLLL_YYYY
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.core_domain.formatToDateString
import kovp.trainhard.training_calendar_presentation.day.Day
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle.SHORT
import java.util.Locale

//TODO: убрать хардкод
private const val HARDCODED_MONTHS = 24L

@Composable
fun CalendarData(
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(HARDCODED_MONTHS) }
    val endMonth = remember { currentMonth }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = firstDayOfWeekFromLocale()) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    VerticalCalendar(
        modifier = Modifier.fillMaxSize(),
        state = state,
        dayContent = { day ->
            if (day.position != MonthDate) return@VerticalCalendar
            val trainings = muscleGroups[day.date].orEmpty()
            Day(groups = trainings, day = day, onClick = onDayClick)
        },
        monthContainer = { calendarMonth, container ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = calendarMonth.yearMonth
                    .formatToDateString(DATE_FORMAT_LLLL_YYYY)
                    .replaceFirstChar(Char::uppercase),
                style = themeTypography.body1,
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
            ) {
                daysOfWeek.forEach { d ->
                    Text(
                        modifier = Modifier.weight(1f),
                        text = d.getDisplayName(SHORT, Locale.getDefault())
                            .replaceFirstChar(Char::uppercase),
                        style = themeTypography.body2
                            .copy(
                                color = themeColors.white.copy(alpha = .6f),
                            ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            container()
            Spacer(modifier = Modifier.height(32.dp))
        },
    )
}
