package kovp.trainhard.parameters_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.exercise_type.ExerciseCard
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.parameters_presentation.di.parametersModule
import kovp.trainhard.parameters_presentation.exercise_parameters.ExerciseParametersRoute
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Composable
fun ParametersComposable(
    navController: NavController,
) {
    loadKoinModules(parametersModule)

    val vm = koinViewModel<ParametersViewModel>()
    val state by vm.state.collectAsState()

    var isVisible by remember { mutableStateOf(false) }
    var bottomDialogState: DialogState? by remember { mutableStateOf(null) }

    if (isVisible) {
        BottomSheetDialog(
            state = bottomDialogState,
            onPositiveClick = {
                vm.handleAction(action = ParametersAction.OnDialogPositiveClick(bottomDialogState))
                isVisible = false
            },
            onNegativeClick = { isVisible = false },
            onCancel = { isVisible = false },
        )
    }

    SubscribeOnEvents(
        eventFlow = vm.eventFlow,
        action = { event ->
            when (event) {
                is ParametersEvent.ShowBottomSheetDialog -> {
                    bottomDialogState = event.dialogState
                    isVisible = true
                }

                is ParametersEvent.NavigateToExerciseParams -> {
                    navController.navigate(
                        ExerciseParametersRoute(
                            title = event.arg.title,
                            muscleIds = event.arg.muscleIds,
                        )
                    )
                }
            }
        },
    )

    ScreenContent(
        state = state,
        handleAction = vm::handleAction,
    )
}

@Composable
private fun ScreenContent(
    state: ParametersScreenState,
    modifier: Modifier = Modifier,
    handleAction: (ParametersAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Text(
                modifier = Modifier
                    .background(color = themeColors.black)
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.exercises_list),
                style = themeTypography.header1.copy(color = themeColors.lime),
            )
        },
        floatingActionButton = {
            TrainFab(icon = Icons.Filled.Add) {
                handleAction(
                    ParametersAction.OnAddButtonClick
                )
            }
        }
    ) {
        StateContainer(
            modifier = Modifier
                .background(color = themeColors.black)
                .padding(it),
            state = state,
        ) { st ->
            when (st) {
                is ParametersScreenState.Loading -> {
                    FullscreenLoader()
                }

                is ParametersScreenState.Data -> {
                    DataContent(
                        state = st,
                        handleAction = handleAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun DataContent(
    state: ParametersScreenState.Data,
    handleAction: (ParametersAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                    handleAction(
                        ParametersAction.OnExerciseClick(exercise = exerciseCard),
                    )
                },
                onRemoveClick = { exerciseCard ->
                    handleAction(
                        ParametersAction.OnDeleteExerciseClicked(exerciseCard)
                    )
                },
            )
        }
    }
}
