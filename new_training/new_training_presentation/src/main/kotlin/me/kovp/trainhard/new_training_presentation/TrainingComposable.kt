package me.kovp.trainhard.new_training_presentation

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr
import me.kovp.trainhard.components.fab.TrainFab
import me.kovp.trainhard.components.train_card.CompletedExerciseCard
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.new_training_api.NewSetDialogScreen
import me.kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction
import me.kovp.trainhard.new_training_api.SelectExerciseTypeScreen
import me.kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import me.kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import me.kovp.trainhard.new_training_presentation.di.newTrainingModule
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult
import me.kovp.trainhard.ui_theme.providers.themeColors
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Destination
@Composable
fun TrainingComposable(
    currentDateString: String,
    navigator: DestinationsNavigator,
    editSetResRecipient: ResultRecipient<NewSetDialogDestination, NewSetDialogResult>,
    newSetResRecipient: ResultRecipient<SelectNewExerciseTypeComposableDestination, NewSetDialogResult>,
) {
    loadKoinModules(
        newTrainingModule(currentDate = currentDateString),
    )

    val mapper = localScreenMapper.current

    val addNewSetScreen = remember { mapper(SelectExerciseTypeScreen) }

    val vm = koinViewModel<TrainingViewModel>()

    val state by vm.stateFlow.collectAsState()

    editSetResRecipient.onNavResult {
        val result = it.getOr { NewSetDialogResult.Error } as? NewSetDialogResult.Success
            ?: return@onNavResult
        vm.addOrEditSet(result)
    }

    newSetResRecipient.onNavResult {
        val result = it.getOr { NewSetDialogResult.Error } as? NewSetDialogResult.Success
            ?: return@onNavResult
        vm.addNewCompletedExercise(dialogResult = result)
    }

    Scaffold(
        floatingActionButton = {
            TrainFab(icon = Icons.Default.Add) {
                navigator.navigate(addNewSetScreen)
            }
        }
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

                val newRepsDialog = NewSetDialogScreen(
                    id = it.setId,
                    exerciseTitle = it.exerciseTitle,
                    initWeight = initWeight,
                    initReps = initReps,
                    requestAction = RequestAction.ADD,
                )
                    .let(mapper::invoke)

                CompletedExerciseCard(
                    card = it,
                    onAddSetClick = {
                        navigator.navigate(newRepsDialog)
                    },
                    onRemoveSetClick = { id ->
                        vm.removeSet(setDto = it, setIndex = id)
                    },
                    onEditSetClick = { id ->
                        val editDialog = NewSetDialogScreen(
                            id = it.setId,
                            setId = id.toLong(),
                            exerciseTitle = it.exerciseTitle,
                            initWeight = initWeight,
                            initReps = initReps,
                            requestAction = RequestAction.EDIT,
                        )
                            .let(mapper::invoke)

                        navigator.navigate(editDialog)
                    }
                )
            }
        }
    }
}
