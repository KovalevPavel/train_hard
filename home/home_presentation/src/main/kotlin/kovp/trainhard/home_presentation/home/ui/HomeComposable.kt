package kovp.trainhard.home_presentation.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.home_presentation.R
import kovp.trainhard.home_presentation.components.GymCardHealth
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
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.time.Duration.Companion.milliseconds

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

    StateContainer(state = state) {
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

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    val startDate = screenState.gymHealth.start
                    val endDate = screenState.gymHealth.end

                    val cardHealth = if (startDate == null || endDate == null) {
                        null
                    } else {
                        val currentDays = System.currentTimeMillis().milliseconds.inWholeDays.toFloat()
                        val startDays = startDate.milliseconds.inWholeDays
                        val endDays = endDate.milliseconds.inWholeDays

                        val raw = (endDays - currentDays) / (endDays - startDays)
                        raw.takeIf { it > 0f }?.coerceAtMost(1f)
                    }

                    GymCardHealth(
                        modifier = Modifier.padding(end = 16.dp).fillMaxWidth(),
                        cardHealth = cardHealth,
                        onClick = {
                            handleAction(HomeAction.OnGymCardPlateClick)
                        },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                containerColor = themeColors.lime,
                shape = RoundedCornerShape(size = 100.dp),
                onClick = {
                    handleAction(HomeAction.OnStartTrainingClick)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.start_training_fab),
                    style = themeTypography.body1.copy(
                        fontSize = 14.sp,
                        color = themeColors.black,
                    ),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = themeColors.black,
    ) {
        HomeScreen(
            todayPlan = screenState.todayPlan,
            dateString = screenState.dateString,
            modifier = Modifier.padding(top = it.calculateTopPadding()).fillMaxSize(),
            onDateClick = {
                handleAction(HomeAction.OnCalendarClick)
            },
        )
    }
}

private fun checkCardHealthUpdates(
    currentCardHealth: HomeScreenState.GymCardDates,
    navController: NavController,
    onAction: (HomeAction.EditGymCardDates) -> Unit,
) {
    navController.currentBackStackEntry?.savedStateHandle
        ?.get<DateRangeSelectorState>(SelectGymDatesScreen.DATE_RANGE_KEY)
        ?.let { (start, end) ->
            val (currentStart, currentEnd) = currentCardHealth
            if (start != currentStart || end != currentEnd) {
                onAction(
                    HomeAction.EditGymCardDates(start ?: return@let, end ?: return@let)
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
