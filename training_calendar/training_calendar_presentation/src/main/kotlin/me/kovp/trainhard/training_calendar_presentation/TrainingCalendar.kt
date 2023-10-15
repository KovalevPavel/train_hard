package me.kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.ramcosta.composedestinations.annotation.Destination
import me.kovp.trainhard.components.muscle_groups_cloud.getMuscleGroupTitle
import me.kovp.trainhard.core_domain.DATE_FORMAT_LLLL_yyyy
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.formatToDateString
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import me.kovp.training_calendar_presentation.R
import timber.log.Timber
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Destination
@Composable
fun TrainingCalendar() {
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
            dayContent = { Day(it) },
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Legend(
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        MuscleGroup.values().forEach { MuscleGroupLegendElement(it) }
    }
}

@Composable
private fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                Timber.e(day.date.toString())
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = themeTypography.body2,
        )
    }
}

@Composable
private fun MuscleGroupLegendElement(muscleGroup: MuscleGroup) {
    val argbColor = when (muscleGroup) {
        MuscleGroup.ARMS -> themeColors.yellow
        MuscleGroup.CHEST -> themeColors.red
        MuscleGroup.BACK -> themeColors.blue
        MuscleGroup.LEGS -> themeColors.lime
        MuscleGroup.DELTOIDS -> themeColors.orange
        MuscleGroup.ABS -> themeColors.white
    }
        .toArgb()

    val title = muscleGroup.getMuscleGroupTitle()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier.size(16.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(),
            ) {
                drawRoundRect(
                    color = Color(argbColor),
                    size = size,
                    cornerRadius = CornerRadius(size.height / 2, size.width / 2),
                )
            }
        }

        Text(
            text = title,
            style = themeTypography.body3,
        )
    }
}
