package me.kovp.trainhard.home_presentation

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.home_presentation.R
import me.kovp.trainhard.components.progress.FullscreenLoader
import me.kovp.trainhard.core_domain.DATE_FORMAT_dd_MM_yyyy
import me.kovp.trainhard.core_domain.DATE_FORMAT_dd_newLine_MMMM
import me.kovp.trainhard.core_domain.formatToDateString
import me.kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import me.kovp.trainhard.home_presentation.TodayPlan.RestDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import me.kovp.trainhard.home_presentation.components.CurrentDateCard
import me.kovp.trainhard.home_presentation.components.ExerciseCard
import me.kovp.trainhard.home_presentation.components.GymCardHealth
import me.kovp.trainhard.home_presentation.di.homeModule
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.new_training_api.TrainingScreen
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Destination
@Composable
fun HomeComposable(
    navigator: DestinationsNavigator,
) {
    loadKoinModules(homeModule)

    val vm = koinViewModel<HomeViewModel>()
    val screenState by vm.stateFlow.collectAsState()
    val action by vm.actionFlow.collectAsState(initial = HomeAction.Empty)

    when (val st = screenState) {
        is HomeScreenState.Loading -> {
            FullscreenLoader()
        }

        is HomeScreenState.Data -> {
            DataContent(screenState = st, viewModel = vm)
        }
    }

    SubscribeToAction(action = action, navigator = navigator)
}

@Composable
private fun SubscribeToAction(
    action: HomeAction,
    navigator: DestinationsNavigator,
) {
    val screenMapper = localScreenMapper.current
    val currentDateString = System.currentTimeMillis().formatToDateString(DATE_FORMAT_dd_MM_yyyy)

    val newTrainingDestination = remember {
        screenMapper(TrainingScreen(dateString = currentDateString))
    }

    when (action) {
        is HomeAction.Empty -> {
            // do nothing
        }

        is HomeAction.OpenNewTrainingScreen -> {
            navigator.navigate(newTrainingDestination)
        }
    }
}

@Composable
private fun DataContent(
    screenState: HomeScreenState.Data,
    viewModel: HomeViewModel,
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                containerColor = themeColors.lime,
                shape = RoundedCornerShape(size = 100.dp),
                onClick = {
                    viewModel.obtainEvent(HomeEvent.OnStartTrainingClick)
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
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun HomeScreen(
    screenState: HomeScreenState.Data,
    modifier: Modifier,
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
            GymCardHealth(cardHealth = cardHealth)
        }
        item {
            CurrentDateCard(
                currentDate = dateString.formatToDateString(DATE_FORMAT_dd_newLine_MMMM, locale),
                currentProgramName = ""
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
