package kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.training_calendar_presentation.di.trainingCalendarModule
import kovp.trainhard.training_calendar_presentation.legend.Legend
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import java.time.LocalDate

@Composable
fun TrainingCalendar(
    navController: NavController,
) {
    loadKoinModules(trainingCalendarModule)
    val viewModel = koinViewModel<TrainingCalendarViewModel>()
    val state by viewModel.state.collectAsState()

    SubscribeOnEvents(
        eventFlow = viewModel.eventFlow,
        action = { handleEvent(event = it, navController = navController) }
    )

    StateContainer(state = state) { trainingCalendarState ->
        when (trainingCalendarState) {
            is TrainingCalendarState.Loading -> {
                FullscreenLoader()
            }

            is TrainingCalendarState.Data -> {
                Data(muscleGroups = trainingCalendarState.trainings) { day ->
                    TrainingCalendarAction.OnTrainingDayClick(day = day)
                        .let(viewModel::handleAction)
                }
            }
        }
    }
}

@Composable
private fun Data(
    muscleGroups: Map<LocalDate, List<MuscleGroup>>,
    onDayClick: (LocalDate) -> Unit,
) {
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

        CalendarData(muscleGroups = muscleGroups, onDayClick = onDayClick)
    }
}

private fun handleEvent(event: TrainingCalendarEvent, navController: NavController) {
    when (event) {
        is TrainingCalendarEvent.OpenNewTrainingScreen -> {
//            TrainingScreen(timestamp = ac.timestamp)
//                .let(screenMapper::invoke)
//                .let(navigator::navigate)
        }
    }
}
