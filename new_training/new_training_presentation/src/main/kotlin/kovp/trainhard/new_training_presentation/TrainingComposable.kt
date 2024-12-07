package kovp.trainhard.new_training_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.train_card.CompletedExerciseCard
import trainhard.kovp.core.RequestAction
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.new_training_presentation.TrainingAction.OnRemoveSetClick
import kovp.trainhard.new_training_presentation.di.newTrainingModule
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TrainingComposable(
    currentTimestamp: Long,
    navController: NavController,
) {
    rememberKoinModules { listOf(newTrainingModule(currentTimestamp = currentTimestamp)) }
    val vm = koinViewModel<TrainingViewModel>()
    val state by vm.state.collectAsState()

    SubscribeOnEvents(
        eventFlow = vm.eventFlow,
        action = { handleEvent(event = it, navController = navController) },
    )

//    editSetResRecipient.onNavResult {
//        val result = it.getOr { NewSetDialogResult.Error } as? NewSetDialogResult.Success
//            ?: run {
//                vm.obtainEvent(null)
//                return@onNavResult
//            }
//        TrainingEvent.AddOrEditSet(result).let(vm::obtainEvent)
//    }

//    newSetResRecipient.onNavResult {
//        val result = it.getOr { NewSetDialogResult.Error } as? NewSetDialogResult.Success
//            ?: run {
//                vm.obtainEvent(null)
//                return@onNavResult
//            }
//        TrainingEvent.AddNewCompletedExercise(result).let(vm::obtainEvent)
//    }

    StateContainer(state = state) {
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

@Composable
private fun DataContent(
    state: TrainingScreenState.Data,
    onAction: (TrainingAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(themeColors.black).fillMaxSize(),
        floatingActionButton = {
            TrainFab(icon = Icons.Default.Add) {
                onAction(TrainingAction.OnAddExerciseClick)
            }
        },
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .background(themeColors.black)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.items) {
                val (initWeight, initReps) = it.sets
                    .lastOrNull()
                    ?: (0f to 0)

                val newSetDialog = NewSetDialogScreen(
                    id = it.setId,
                    exerciseTitle = it.exerciseTitle,
                    initWeight = initWeight,
                    initReps = initReps,
                    requestAction = RequestAction.Add,
                )
                    .let(TrainingAction::NavigateToSetDialog)

                CompletedExerciseCard(
                    card = it,
                    onAddSetClick = {
                        onAction(newSetDialog)
                    },
                    onRemoveSetClick = { id ->
                        onAction(OnRemoveSetClick(setDto = it, setIndex = id))
                    },
                    onEditSetClick = { id ->
                        NewSetDialogScreen(
                            id = it.setId,
                            setId = id.toLong(),
                            exerciseTitle = it.exerciseTitle,
                            initWeight = initWeight,
                            initReps = initReps,
                            requestAction = RequestAction.Edit,
                        )
                            .let(TrainingAction::NavigateToSetDialog)
                            .let { onAction(it) }
                    }
                )
            }
        }
    }
}

private fun handleEvent(
    event: TrainingEvent,
    navController: NavController,
) {
    when (event) {
        is TrainingEvent.NavigateToSelectExerciseType -> {
            navController.navigate(SelectExerciseTypeScreen)
        }

        is TrainingEvent.NavigateToEditSetDialog -> {

        }
    }
}
