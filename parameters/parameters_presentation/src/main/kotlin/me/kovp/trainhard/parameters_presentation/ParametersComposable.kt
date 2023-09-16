package me.kovp.trainhard.parameters_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr
import me.kovp.trainhard.components.exercise_type.ExerciseCard
import me.kovp.trainhard.components.fab.TrainFab
import me.kovp.trainhard.components.progress.TrainProgressIndicator
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen.RequestAction
import me.kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import me.kovp.trainhard.parameters_presentation.di.parametersModule
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Destination
@Composable
fun ParametersComposable(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<NewExerciseScreenDestination, NewExerciseScreenResult>,
) {
    loadKoinModules(
        parametersModule,
    )

    val screenMapper = localScreenMapper.current

    val vm = koinViewModel<ParametersViewModel>()

    val state by vm.stateFlow.collectAsState()

    resultRecipient.onNavResult {
        val result = it.getOr { NewExerciseScreenResult.Fail } as? NewExerciseScreenResult.Success
            ?: return@onNavResult
        vm.addOrEditExercise(exercise = result)
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.zIndex(2f),
            contentAlignment = Alignment.Center,
        ) {
            TrainProgressIndicator()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                modifier = Modifier
                    .background(color = themeColors.black)
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Список упражнений",
                style = themeTypography.header1.copy(color = themeColors.lime)
            )
        },
        floatingActionButton = {
            TrainFab(icon = Icons.Filled.Add) {
                screenMapper(
                    NewExerciseDialogScreen(requestAction = RequestAction.ADD)
                )
                    .let(navigator::navigate)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = themeColors.black),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 100.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.items) { dto ->
                ExerciseCard(
                    card = dto,
                    onCardClick = { exerciseCard ->
                        screenMapper(
                            NewExerciseDialogScreen(
                                cardTitle = exerciseCard.title,
                                muscleIds = exerciseCard.muscles.map(Muscle::id),
                                requestAction = RequestAction.EDIT,
                            ),
                        )
                            .let(navigator::navigate)
                    },
                    onRemoveClick = { exerciseCard -> vm.removeExercise(exerciseCard) },
                )
            }
        }
    }
}
