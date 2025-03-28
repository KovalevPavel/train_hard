package kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import kovp.trainhard.new_training_api.TrainingScreen
import kovp.trainhard.training_calendar_presentation.di.trainingCalendarModule
import kovp.trainhard.training_calendar_presentation.legend.Legend
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI
import java.time.LocalDate

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TrainingCalendar(
    navController: NavController,
) {
    rememberKoinModules { listOf(trainingCalendarModule) }
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
                Data(
                    firstMonthOffset = viewModel.firstMonthsOffset,
                    state = trainingCalendarState,
                ) { day ->
                    TrainingCalendarAction.OnTrainingDayClick(day = day)
                        .let(viewModel::handleAction)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Data(
    firstMonthOffset: Long,
    state: TrainingCalendarState.Data,
    onDayClick: (LocalDate) -> Unit,
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(color = themeColors.black)
                    .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = stringResource(id = R.string.training_calendar_title),
                    style = themeTypography.header1.copy(color = themeColors.lime),
                )

                Legend(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    muscleGroups = state.muscleGroups,
                )
            }
        },
        containerColor = themeColors.black
    ) {
        CalendarData(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            firstMonthOffset = firstMonthOffset,
            muscleGroups = state.trainings,
            onDayClick = onDayClick,
        )
    }
}

private fun handleEvent(event: TrainingCalendarEvent, navController: NavController) {
    when (event) {
        is TrainingCalendarEvent.OpenNewTrainingScreen -> {
            navController.navigate(TrainingScreen(timestamp = event.timestamp))
        }
    }
}
