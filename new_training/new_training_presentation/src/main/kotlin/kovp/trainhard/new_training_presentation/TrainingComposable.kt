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
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.train_card.CompletedExerciseCard
import kovp.trainhard.new_training_api.NewSetDialogScreen
import kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction
import kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction.EDIT
import kovp.trainhard.new_training_presentation.TrainingAction.OnRemoveSetClick
import kovp.trainhard.new_training_presentation.di.newTrainingModule
import kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Composable
fun TrainingComposable(
    currentTimestamp: Long,
) {
    loadKoinModules(
        newTrainingModule(currentTimestamp = currentTimestamp),
    )

    val vm = koinViewModel<TrainingViewModel>()

    val action by vm.actionFlow.collectAsState(initial = TrainingEvent.Empty)

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

    StateContainer(state = vm.state) {
        when (it) {
            is TrainingScreenState.Loading -> {
                FullscreenLoader()
            }

            is TrainingScreenState.Data -> {
                DataContent(state = it, vm = vm)
            }
        }
    }

//    SubscribeToActions(action = action, navigator = navigator)
}

//@Composable
//private fun SubscribeToActions(
//    action: TrainingAction,
//    navigator: DestinationsNavigator,
//) {
//    val mapper = localScreenMapper.current
//    val addNewSetScreen = remember { mapper(SelectExerciseTypeScreen) }
//
//    when (action) {
//        is TrainingAction.Empty -> {
//            // do nothing
//        }
//
//        is TrainingAction.NavigateToSelectExerciseType -> {
//            navigator.navigate(addNewSetScreen)
//        }
//
//        is TrainingAction.NavigateToEditSetDialog -> {
//            mapper(action.data)
//                .let(navigator::navigate)
//        }
//    }
//}

@Composable
private fun DataContent(
    state: TrainingScreenState.Data,
    vm: TrainingViewModel,
) {
    Scaffold(
        floatingActionButton = {
            TrainFab(icon = Icons.Default.Add) {
                TrainingAction.OnAddExerciseClick.let(vm::handleAction)
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
                    requestAction = RequestAction.ADD,
                )
                    .let(TrainingAction::NavigateToSetDialog)

                CompletedExerciseCard(
                    card = it,
                    onAddSetClick = {
                        vm.handleAction(newSetDialog)
                    },
                    onRemoveSetClick = { id ->
                        OnRemoveSetClick(setDto = it, setIndex = id)
                            .let(vm::handleAction)
                    },
                    onEditSetClick = { id ->
                        NewSetDialogScreen(
                            id = it.setId,
                            setId = id.toLong(),
                            exerciseTitle = it.exerciseTitle,
                            initWeight = initWeight,
                            initReps = initReps,
                            requestAction = EDIT,
                        )
                            .let(TrainingAction::NavigateToSetDialog)
                            .let(vm::handleAction)
                    }
                )
            }
        }
    }
}
