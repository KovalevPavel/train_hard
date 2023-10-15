package me.kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.ramcosta.composedestinations.annotation.Destination
import me.kovp.trainhard.components.progress.FullscreenLoader
import me.kovp.trainhard.core_domain.DATE_FORMAT_LLLL_yyyy
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.formatToDateString
import me.kovp.trainhard.training_calendar_presentation.day.Day
import me.kovp.trainhard.training_calendar_presentation.di.trainingCalendarModule
import me.kovp.trainhard.training_calendar_presentation.legend.Legend
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import me.kovp.training_calendar_presentation.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Destination
@Composable
fun TrainingCalendar() {
    loadKoinModules(trainingCalendarModule)
    val viewModel = koinViewModel<TrainingCalendarViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    when (val st = state) {
        is TrainingCalendarState.Loading -> {
            FullscreenLoader()
        }

        is TrainingCalendarState.Data -> {
            Data(muscleGroups = st.trainings) { day ->
                TrainingCalendarEvent.OnTrainingDayClick(day = day).let(viewModel::obtainEvent)
            }
        }
    }
}

@Composable
fun Data(
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(24) }
    val endMonth = remember { currentMonth }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = firstDayOfWeekFromLocale()) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    Column(
        modifier = Modifier
            .background(color = themeColors.black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = stringResource(id = R.string.training_calendar_title),
            style = themeTypography.header1.copy(color = themeColors.lime),
        )

        Legend(
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        VerticalCalendar(
            modifier = Modifier.fillMaxSize(),
            state = state,
            dayContent = { day ->
                if (day.position != DayPosition.MonthDate) return@VerticalCalendar
                val trainings = muscleGroups[day.date].orEmpty()
                Day(groups = trainings, day = day, onClick = onDayClick)
            },
            monthContainer = { calendarMonth, container ->
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = calendarMonth.yearMonth
                        .formatToDateString(DATE_FORMAT_LLLL_yyyy)
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
                Spacer(modifier = Modifier.height(32.dp))
            },
        )
    }
}
