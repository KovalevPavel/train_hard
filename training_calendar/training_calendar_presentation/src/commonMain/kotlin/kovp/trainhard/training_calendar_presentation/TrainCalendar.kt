package kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.view.CalendarView
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.training_calendar_presentation.day.Day
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.jetbrains.compose.resources.stringResource
import trainhard.training_calendar_presentation.generated.resources.Res
import trainhard.training_calendar_presentation.generated.resources.locale_apr
import trainhard.training_calendar_presentation.generated.resources.locale_aug
import trainhard.training_calendar_presentation.generated.resources.locale_dec
import trainhard.training_calendar_presentation.generated.resources.locale_feb
import trainhard.training_calendar_presentation.generated.resources.locale_fri
import trainhard.training_calendar_presentation.generated.resources.locale_jan
import trainhard.training_calendar_presentation.generated.resources.locale_jul
import trainhard.training_calendar_presentation.generated.resources.locale_jun
import trainhard.training_calendar_presentation.generated.resources.locale_mar
import trainhard.training_calendar_presentation.generated.resources.locale_may
import trainhard.training_calendar_presentation.generated.resources.locale_mon
import trainhard.training_calendar_presentation.generated.resources.locale_nov
import trainhard.training_calendar_presentation.generated.resources.locale_oct
import trainhard.training_calendar_presentation.generated.resources.locale_sat
import trainhard.training_calendar_presentation.generated.resources.locale_sep
import trainhard.training_calendar_presentation.generated.resources.locale_sun
import trainhard.training_calendar_presentation.generated.resources.locale_thu
import trainhard.training_calendar_presentation.generated.resources.locale_tue
import trainhard.training_calendar_presentation.generated.resources.locale_wed

@Composable
fun TrainCalendar(
    modifier: Modifier,
    firstMonthOffset: Long,
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit,
) {
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    LazyCalendar(
        modifier = modifier,
        firstMonthOffset = firstMonthOffset.toInt(),
    ) {
        CalendarView(
            config = rememberCalendarState(
                minDate = currentDate.minus(DatePeriod(months = firstMonthOffset.toInt())),
                maxDate = currentDate,
                startDate = currentDate,
                monthOffset = it,
                showNextMonthDays = false,
                showPreviousMonthDays = false,
                showHeader = true,
                showWeekdays = true,
            ),
            header = { month, year -> MonthTitle(month, year) },
            dayOfWeekLabel = { DayOfWeek(it) },
            day = { day ->
                Day(
                    groups = muscleGroups[day.date].orEmpty(),
                    date = day.date,
                    onClick = onDayClick,
                )
            },
            modifier = Modifier.height(410.dp),
        )
    }
}

@Composable
private fun LazyCalendar(
    modifier: Modifier = Modifier,
    firstMonthOffset: Int,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    calendarView: @Composable (monthOffset: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        reverseLayout = true,
    ) {
        items(firstMonthOffset) {
            calendarView(-it)
        }
    }
}

@Composable
private fun DayOfWeek(day: DayOfWeek) {
    val resList = remember {
        listOf(
            Res.string.locale_mon,
            Res.string.locale_tue,
            Res.string.locale_wed,
            Res.string.locale_thu,
            Res.string.locale_fri,
            Res.string.locale_sat,
            Res.string.locale_sun,
        )
    }

    val dayName = stringResource(resList[day.ordinal])
    Text(
//        modifier = Modifier.weight(1f),
        text = dayName
            .replaceFirstChar(Char::uppercase),
        style = themeTypography.body2
            .copy(
                color = themeColors.white.copy(alpha = .6f),
            ),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun MonthTitle(month: Month, year: Int) {
    val resList = remember {
        listOf(
            Res.string.locale_jan,
            Res.string.locale_feb,
            Res.string.locale_mar,
            Res.string.locale_apr,
            Res.string.locale_may,
            Res.string.locale_jun,
            Res.string.locale_jul,
            Res.string.locale_aug,
            Res.string.locale_sep,
            Res.string.locale_oct,
            Res.string.locale_nov,
            Res.string.locale_dec,
        )
    }

    val monthName = stringResource(resList[month.ordinal])

    Text(
        text = "$monthName $year",
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 20.dp, top = 0.dp, start = 16.dp),
        style = themeTypography.header2,
        color = themeColors.lime
    )
}
