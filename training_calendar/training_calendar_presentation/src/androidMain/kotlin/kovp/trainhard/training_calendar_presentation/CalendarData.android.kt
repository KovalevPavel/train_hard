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
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toKotlinLocalDate
import kovp.trainhard.core_domain.DATE_FORMAT_LLLL_YYYY
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.core_domain.formatToDateString
import kovp.trainhard.training_calendar_presentation.day.Day
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
actual fun CalendarData(
    modifier: Modifier,
    firstMonthOffset: Long,
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(firstMonthOffset) }
    val endMonth = remember { currentMonth }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    VerticalCalendar(
        modifier = modifier.fillMaxSize(),
        state = state,
        dayContent = { day ->
            if (day.position != DayPosition.MonthDate) return@VerticalCalendar
            val trainings = muscleGroups.mapKeys { it.key.toJavaLocalDate() }[day.date].orEmpty()

            val mDay = object : kovp.trainhard.training_calendar_presentation.day.CalendarDay {
                override val date: LocalDate = day.date.toKotlinLocalDate()
            }

            Day(groups = trainings, day = mDay, onClick = onDayClick)
        },
        monthContainer = { calendarMonth, container ->
            Spacer(modifier = Modifier.height(8.dp))
            val dateString = Date.from(
                calendarMonth.yearMonth
                    .atDay(1)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
            )
                .toInstant()
                .toKotlinInstant()
                .formatToDateString(DATE_FORMAT_LLLL_YYYY)
                .replaceFirstChar(Char::uppercase)

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = dateString,
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
                        text = d.getDisplayName(TextStyle.SHORT, Locale.getDefault())
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
            Spacer(modifier = Modifier.height(24.dp))
        },
    )
}