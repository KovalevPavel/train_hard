package kovp.trainhard.new_training_presentation.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.train_card.CompletedExerciseCard
import kovp.trainhard.core_presentation.subscribeForResult
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.new_training_presentation.SelectExerciseTypeScreen
import kovp.trainhard.new_training_presentation.screen.TrainingAction.OnRemoveSetClick
import kovp.trainhard.new_training_presentation.di.newTrainingModule
import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogResult
import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogVs
import kovp.trainhard.new_training_presentation.edit_set_dialog.ui.EditSetDialogComposable
import kovp.trainhard.new_training_presentation.screen.TrainingAction
import kovp.trainhard.new_training_presentation.screen.TrainingEvent
import kovp.trainhard.new_training_presentation.screen.TrainingScreenState
import kovp.trainhard.new_training_presentation.screen.TrainingViewModel
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI
import trainhard.kovp.core.RequestAction

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TrainingComposable(
    currentTimestamp: Long,
    navController: NavController,
) {
    rememberKoinModules { listOf(newTrainingModule(currentTimestamp = currentTimestamp)) }
    val vm = koinViewModel<TrainingViewModel>()
    val state by vm.state.collectAsState()
    var editSetDialogState by remember { mutableStateOf<EditSetDialogVs?>(null) }

    SubscribeOnEvents(
        eventFlow = vm.eventFlow,
        action = {
            handleEvent(
                event = it,
                navController = navController,
                onNewEditSetDialogState = { st -> editSetDialogState = st },
            )
        },
    )

    EditSetDialog(
        state = editSetDialogState,
        onDismiss = { editSetDialogState = null },
    ) { result, action ->
        editSetDialogState = null
        if (result !is EditSetDialogResult.Success) {
            return@EditSetDialog
        }

        when (action) {
            RequestAction.Add -> TrainingAction.AddNewCompletedExercise(data = result)
            RequestAction.Edit -> TrainingAction.AddOrEditSet(data = result)
        }
            .let(vm::handleAction)
    }

    navController.subscribeForResult<String>(SelectExerciseTypeScreen.EXERCISE_TITLE_ID) {
        EditSetDialogVs(
            exerciseTitle = it,
            requestAction = RequestAction.Add,
        )
            .let(TrainingAction::NavigateToSetDialog)
            .let(vm::handleAction)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = themeColors.black,
        floatingActionButton = {
            AnimatedVisibility(
                visible = state is TrainingScreenState.Data,
                enter = scaleIn(tween(500)),
                exit = scaleOut(tween(500)),
            ) {
                TrainFab(icon = Icons.Default.Add) {
                    TrainingAction.OnAddExerciseClick.let(vm::handleAction)
                }
            }
        },
    ) { paddings ->
        StateContainer(
            modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            state = state
        ) {
            when (it) {
                is TrainingScreenState.Loading -> {
                    FullscreenLoader()
                }

                is TrainingScreenState.Data -> {
                    DataContent(state = it, onAction = vm::handleAction)
                }
            }
        }
    }
}

@Composable
private fun DataContent(
    state: TrainingScreenState.Data,
    onAction: (TrainingAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.items) {
            val (initWeight, initReps) = it.sets
                .lastOrNull()
                ?: (0f to 0)

            val newSetDialogAction = EditSetDialogVs(
                id = it.setId,
                exerciseTitle = it.exerciseTitle,
                initWeight = initWeight,
                initReps = initReps,
                requestAction = RequestAction.Edit,
            )
                .let(TrainingAction::NavigateToSetDialog)

            CompletedExerciseCard(
                card = it,
                onAddSetClick = {
                    onAction(newSetDialogAction)
                },
                onRemoveSetClick = { id ->
                    onAction(OnRemoveSetClick(setDto = it, setIndex = id))
                },
                onEditSetClick = { id ->
                    EditSetDialogVs(
                        id = it.setId,
                        setId = id.toLong(),
                        exerciseTitle = it.exerciseTitle,
                        initWeight = initWeight,
                        initReps = initReps,
                        requestAction = RequestAction.Edit,
                    )
                        .let(TrainingAction::NavigateToSetDialog)
                        .let { a -> onAction(a) }
                }
            )
        }
    }
}

private fun handleEvent(
    event: TrainingEvent,
    navController: NavController,
    onNewEditSetDialogState: (EditSetDialogVs?) -> Unit,
) {
    when (event) {
        is TrainingEvent.NavigateToSelectExerciseType -> {
            onNewEditSetDialogState(null)
            navController.navigate(SelectExerciseTypeScreen)
        }

        is TrainingEvent.NavigateToEditSetDialog -> {
            onNewEditSetDialogState(event.data)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditSetDialog(
    state: EditSetDialogVs?,
    onDismiss: () -> Unit,
    onApplyClick: (EditSetDialogResult, RequestAction) -> Unit,
) {
    if (state != null) {
        BasicAlertDialog(
            onDismissRequest = onDismiss,
        ) {
            EditSetDialogComposable(
                exerciseId = state.id,
                setId = state.setId,
                exerciseTitle = state.exerciseTitle,
                initWeight = state.initWeight,
                initReps = state.initReps,
                onApplyClick = { onApplyClick(it, state.requestAction) },
            )
        }
    }
}
