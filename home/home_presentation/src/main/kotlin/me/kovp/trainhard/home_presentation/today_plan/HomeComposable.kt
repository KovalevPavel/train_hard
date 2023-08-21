package me.kovp.trainhard.home_presentation.today_plan

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.home_presentation.R
import me.kovp.trainhard.core.DATE_FORMAT_dd_newLine_MMMM
import me.kovp.trainhard.core.formatToDateString
import me.kovp.trainhard.home_presentation.destinations.NewTrainingComposableDestination
import me.kovp.trainhard.home_presentation.today_plan.TodayPlan.NoProgramSelected
import me.kovp.trainhard.home_presentation.today_plan.TodayPlan.RestDay
import me.kovp.trainhard.home_presentation.today_plan.TodayPlan.TrainingDay
import me.kovp.trainhard.home_presentation.today_plan.components.CurrentDateCard
import me.kovp.trainhard.home_presentation.today_plan.components.ExerciseCard
import me.kovp.trainhard.home_presentation.today_plan.components.GymCardHealth
import me.kovp.trainhard.home_presentation.di.homeModule
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeComposable(
    navigator: DestinationsNavigator,
) {
    loadKoinModules(homeModule)
    val vm:HomeViewModel = koinViewModel<HomeViewModelImpl>()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                containerColor = themeColors.lime,
                shape = RoundedCornerShape(size = 100.dp),
                onClick = {
                    navigator.navigate(NewTrainingComposableDestination)
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
            viewModel = vm,
            modifier = Modifier.padding(it)
        )
    }

    LaunchedEffect(key1 = vm) {
        println("vm -> ${vm.hashCode()}")
    }
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier,
) {
    val dateString by viewModel.dateStringFlow.collectAsState(initial = null)
    val cardHealth by viewModel.gymHealthFlow.collectAsState(initial = null)
    val plan by viewModel.todayPlanFlow.collectAsState(initial = NoProgramSelected)

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
                currentDate = dateString?.formatToDateString(DATE_FORMAT_dd_newLine_MMMM, locale),
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
