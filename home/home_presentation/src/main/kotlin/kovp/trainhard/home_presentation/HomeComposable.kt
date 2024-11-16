package kovp.trainhard.home_presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.core_domain.DATE_FORMAT_dd_MMMM
import kovp.trainhard.core_domain.formatToDateString
import kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import kovp.trainhard.home_presentation.TodayPlan.RestDay
import kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import kovp.trainhard.home_presentation.components.CurrentDateCard
import kovp.trainhard.home_presentation.components.ExerciseCard
import kovp.trainhard.home_presentation.components.GymCardHealth
import kovp.trainhard.home_presentation.di.homeModule
import kovp.trainhard.home_presentation.gym_card_dates.SelectGymDatesScreen
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.new_training_api.TrainingScreen
import kovp.trainhard.training_calendar_api.TrainingCalendarScreen
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Composable
fun HomeComposable(
    navController: NavController,
) {
    loadKoinModules(homeModule)

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    ) {
        HomeScreen(
            todayPlan = screenState.todayPlan,
            dateString = screenState.dateString,
            gymCardDateDates = screenState.gymHealth,
            modifier = Modifier,
            onGymCardPlateClick = {
                handleAction(HomeAction.OnGymCardPlateClick)
            },
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

@Composable
private fun HomeScreen(
    todayPlan: TodayPlan,
    dateString: Long,
    gymCardDateDates: HomeScreenState.GymCardDates,
    modifier: Modifier,
    onGymCardPlateClick: () -> Unit,
    onDateClick: () -> Unit,
) {

    val locale = LocalContext.current.resources.configuration.locales[0]

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(themeColors.black)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top),
    ) {
        item {
            GymCardHealth(
                startDate = gymCardDateDates.start,
                endDate = gymCardDateDates.end,
                onClick = onGymCardPlateClick,
            )
        }
        item {
            CurrentDateCard(
                currentDate = dateString.formatToDateString(DATE_FORMAT_dd_MMMM, locale),
                currentProgramName = "",
                onDateClick = onDateClick,
            )
        }
        item {
            Text(
                text = stringResource(id = R.string.today_plan),
                style = themeTypography.header1,
            )
        }
        mapTodayPlan(plan = todayPlan)
    }
}

private fun LazyListScope.mapTodayPlan(plan: TodayPlan) {
    when (plan) {
        NoProgramSelected -> {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = stringResource(id = R.string.today_plan_no_selected),
                    style = themeTypography.body2,
                    textAlign = TextAlign.Center,
                )
            }
        }

        RestDay -> {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = stringResource(id = R.string.today_plan_rest_day),
                    style = themeTypography.body2,
                    textAlign = TextAlign.Center,
                )
            }
        }

        is TrainingDay -> {
            items(plan.items, key = { it.id }) {
                ExerciseCard(exercise = it)
            }
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
