package kovp.trainhard.training_calendar_presentation.day

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.training_calendar_presentation.MuscleDot
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun Day(
    groups: List<MuscleGroup>,
    date: LocalDate,
    onClick: (LocalDate) -> Unit,
) {
    val currentDate = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    val isDayEnabled = remember { currentDate >= date }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = isDayEnabled) { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = themeTypography.body2
                    .copy(
                        color = themeColors.white.copy(alpha = if (isDayEnabled) 1f else .6f),
                    ),
            )
            DayLegend(muscleGroups = groups)
        }
    }
}

@Composable
private fun DayLegend(
    modifier: Modifier = Modifier,
    muscleGroups: List<MuscleGroup>,
) {
    if (muscleGroups.isEmpty()) return

    FlowRow(
        modifier = modifier
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        muscleGroups.forEach {
            MuscleDot(
                modifier = Modifier.size(8.dp),
                muscleGroup = it,
            )
        }
    }
}
