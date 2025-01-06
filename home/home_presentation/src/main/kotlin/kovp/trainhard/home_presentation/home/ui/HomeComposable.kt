package kovp.trainhard.home_presentation.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.core_presentation.subscribeForResult
import kovp.trainhard.home_presentation.di.homeModule
import kovp.trainhard.home_presentation.home.presentation.HomeAction
import kovp.trainhard.home_presentation.home.presentation.HomeEvent
import kovp.trainhard.home_presentation.home.presentation.HomeScreenState
import kovp.trainhard.home_presentation.home.presentation.HomeViewModel
import kovp.trainhard.home_presentation.navigation.SelectGymDatesScreen
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.new_training_api.TrainingScreen
import kovp.trainhard.training_calendar_api.TrainingCalendarScreen
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeComposable(
    navController: NavController,
) {
    rememberKoinModules { listOf(homeModule) }

    val vm = koinViewModel<HomeViewModel>()
    val state by vm.state.collectAsState()

    SubscribeOnEvents(
        eventFlow = vm.eventFlow,
        action = { handleEvent(it, navController) },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = themeColors.black,
        topBar = {
            AnimatedVisibility(
                visible = state is HomeScreenState.Data,
                enter = fadeIn() +
                        expandIn(
                            expandFrom = Alignment.TopCenter,
                            initialSize = { IntSize(width = it.width, height = 0) },
                            animationSpec = tween(500),
                        )
            ) {
                (state as? HomeScreenState.Data)?.let { screenState ->
                    HomeTopBar(screenState, vm::handleAction)
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state is HomeScreenState.Data,
                enter = scaleIn(),
                content = { HomeFloating(vm::handleAction) },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddings ->

        StateContainer(
            modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            state = state,
        ) {
            when (it) {
                is HomeScreenState.Loading -> {
                    FullscreenLoader()
                }

                is HomeScreenState.Data -> {
                    DataContent(
                        screenState = it,
                        handleAction = vm::handleAction,
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
private fun DataContent(
    screenState: HomeScreenState.Data,
    navController: NavController,
    handleAction: (HomeAction) -> Unit,
) {
    checkCardHealthUpdates(
        currentCardHealth = screenState.gymHealth,
        navController = navController,
        onAction = handleAction,
    )

    HomeScreen(
        todayPlan = screenState.todayPlan,
        dateString = screenState.dateString,
        onDateClick = {
            handleAction(HomeAction.OnCalendarClick)
        },
    )
}

private fun checkCardHealthUpdates(
    currentCardHealth: HomeScreenState.GymCardDates,
    navController: NavController,
    onAction: (HomeAction.EditGymCardDates) -> Unit,
) {
    navController.subscribeForResult<DateRangeSelectorState>(
        key = SelectGymDatesScreen.DATE_RANGE_KEY,
    ) { (start, end) ->
        val (currentStart, currentEnd) = currentCardHealth
        if (start != currentStart || end != currentEnd) {
            onAction(
                HomeAction.EditGymCardDates(
                    start ?: return@subscribeForResult,
                    end ?: return@subscribeForResult,
                )
            )
        }
    }
}

private fun handleEvent(event: HomeEvent, navController: NavController) {
    when (event) {
        is HomeEvent.OpenDatePickerDialog -> {
            navController.navigate(
                SelectGymDatesScreen(
                    startDate = event.startDate,
                    endDate = event.endDate,
                )
            )
        }

        is HomeEvent.OpenNewTrainingScreen -> {
            navController.navigate(TrainingScreen(timestamp = System.currentTimeMillis()))
        }

        is HomeEvent.OpenTrainingCalendar -> {
            navController.navigate(TrainingCalendarScreen(lastAvailableDate = System.currentTimeMillis()))
        }
    }
}
