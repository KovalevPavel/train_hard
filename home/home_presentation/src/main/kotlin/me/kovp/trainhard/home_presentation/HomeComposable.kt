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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import me.kovp.trainhard.components.train_card.SetCard
import me.kovp.trainhard.components.train_card.SetCardDto
import me.kovp.trainhard.core_domain.DATE_FORMAT_dd_newLine_MMMM
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.formatToDateString
import me.kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import me.kovp.trainhard.home_presentation.TodayPlan.RestDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import me.kovp.trainhard.home_presentation.components.CurrentDateCard
import me.kovp.trainhard.home_presentation.components.ExerciseCard
import me.kovp.trainhard.home_presentation.components.GymCardHealth
import me.kovp.trainhard.home_presentation.di.homeModule
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.new_training_api.NewTrainingScreen
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

@Destination
@Composable
fun HomeComposable(
    navigator: DestinationsNavigator,
) {
    loadKoinModules(homeModule)
    val screenMapper = localScreenMapper.current

    val newTrainingDestination = remember { screenMapper(NewTrainingScreen) }
    val vm: HomeViewModel = koinViewModel<HomeViewModelImpl>()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                containerColor = themeColors.lime,
                shape = RoundedCornerShape(size = 100.dp),
                onClick = {
                    navigator.navigate(newTrainingDestination)
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

    var sets by remember {
        mutableStateOf(
            listOf(
                SetCardDto(
                    exerciseTitle = "Приседания",
                    sets = listOf(
                        "set 1",
                        "set 2",
                        "set 3",
                        "set 4",
                        "set 5",
                    ),
                    muscleGroups = MuscleGroup.Legs.values().map { it.id },
                ),
                SetCardDto(
                    exerciseTitle = "Жим лежа",
                    sets = listOf(
                        "set 1",
                        "set 2",
                        "set 3",
                        "set 4",
                        "set 5",
                    ),
                    muscleGroups = MuscleGroup.Chest.values().map { it.id },
                )
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(themeColors.black)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top),
    ) {
        itemsIndexed(sets) {index, set ->
            SetCard(
                card = set,
                onAddSetClick = {
                    Timber.e("add set for $set")
                },
                onRemoveSetClick = { setIndex ->
                    set.copy(
                        sets = set.sets.filterIndexed { i, _ -> i != setIndex }
                    )
                        .let {
                            sets = sets.mapIndexed { i, s ->
                                if (i == index) it else s
                            }
                        }
                },
                onEditSetClick = { i ->
                    Timber.e("edit set $i for $set")
                }
            )
        }
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
