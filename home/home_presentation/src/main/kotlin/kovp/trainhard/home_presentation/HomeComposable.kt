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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.core_domain.DATE_FORMAT_dd_MMMM
import kovp.trainhard.core_domain.formatToDateString
import kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import kovp.trainhard.home_presentation.TodayPlan.RestDay
import kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import kovp.trainhard.home_presentation.components.CurrentDateCard
import kovp.trainhard.home_presentation.components.ExerciseCard
import kovp.trainhard.home_presentation.components.GymCardHealth
import kovp.trainhard.home_presentation.di.homeModule
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Composable
fun HomeComposable() {
    loadKoinModules(homeModule)

    val vm = koinViewModel<HomeViewModel>()
    val action by vm.actionFlow.collectAsState(initial = HomeEvent.Empty)

//    resultRecipient.onNavResult {
//        val (start, end) = it.getOr {
//            vm.obtainEvent(null)
//            return@onNavResult
//        }
//        HomeEvent.EditGymCardDates(
//            startTimestamp = start ?: return@onNavResult,
//            endTimestamp = end ?: return@onNavResult,
//        )
//            .let(vm::obtainEvent)
//    }

    HomeContent(vm.state, vm::handleAction)

//    SubscribeToAction(action = action, navigator = navigator)
}

@Composable
fun HomeContent(
    state: HomeScreenState,
    handleAction: (HomeAction) -> Unit,
) {
    when (state) {
        is HomeScreenState.Loading -> {
            FullscreenLoader()
        }

        is HomeScreenState.Data -> {
            DataContent(screenState = state, handleAction = handleAction)
        }
    }
}

//@Composable
//private fun SubscribeToAction(
//    action: HomeAction,
//    navigator: DestinationsNavigator,
//) {
//    val screenMapper = localScreenMapper.current
//    val currentDateString = LocalDate.now()
//        .atStartOfDay()
//        .atZone(ZoneId.systemDefault())
//        .toInstant()
//        .toEpochMilli()
//
//    val newTrainingDestination = remember {
//        screenMapper(TrainingScreen(timestamp = currentDateString))
//    }
//
//    val trainingCalendarDestination = remember {
//        screenMapper(TrainingCalendarScreen(0))
//    }
//
//    when (action) {
//        is HomeAction.Empty -> {
//            // do nothing
//        }
//
//        is HomeAction.OpenDatePickerDialog -> {
//            SelectGymDatesScreen(
//                startDate = action.startDate,
//                endDate = action.endDate,
//            )
//                .let(screenMapper::invoke)
//                .let(navigator::navigate)
//        }
//
//        is HomeAction.OpenNewTrainingScreen -> {
//            navigator.navigate(newTrainingDestination)
//        }
//
//        is HomeAction.OpenTrainingCalendar -> {
//            navigator.navigate(trainingCalendarDestination)
//        }
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DataContent(
    screenState: HomeScreenState.Data,
    handleAction: (HomeAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(color = Color.Cyan),
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
            screenState = screenState,
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

@Composable
private fun HomeScreen(
    screenState: HomeScreenState.Data,
    modifier: Modifier,
    onGymCardPlateClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    val dateString = screenState.dateString
    val cardHealth = screenState.gymHealth
    val plan = screenState.todayPlan

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
                startDate = cardHealth?.start,
                endDate = cardHealth?.end,
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
        mapTodayPlan(plan = plan)
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
