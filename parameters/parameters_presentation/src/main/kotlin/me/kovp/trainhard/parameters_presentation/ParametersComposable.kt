package me.kovp.trainhard.parameters_presentation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr
import me.kovp.parameters_presentation.R
import me.kovp.trainhard.components.exercise_type.ExerciseCard
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto.MuscleDto
import me.kovp.trainhard.components.fab.TrainFab
import me.kovp.trainhard.components.progress.FullscreenLoader
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen.RequestAction
import me.kovp.trainhard.parameters_presentation.ParametersViewModel.Companion.CONFIRM_DELETE_EXERCISE_DIALOG_LABEL
import me.kovp.trainhard.parameters_presentation.ParametersViewModel.Companion.EXERCISE_ALREADY_EXISTS_DIALOG_LABEL
import me.kovp.trainhard.parameters_presentation.destinations.AlertConfirmationDialogDestination
import me.kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import me.kovp.trainhard.parameters_presentation.di.parametersModule
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import trainhard.core_dialogs.AlertConfirmationDialogScreen

@Destination
@Composable
fun ParametersComposable(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<NewExerciseScreenDestination, NewExerciseScreenResult>,
    confirmationResultRecipient: ResultRecipient<AlertConfirmationDialogDestination, Boolean>,
) {
    loadKoinModules(
        parametersModule,
    )

    val vm = koinViewModel<ParametersViewModel>()

    val state by vm.stateFlow.collectAsState()
    val action by vm.actionFlow.collectAsState(initial = ParametersAction.Empty)

    resultRecipient.onNavResult {
        val result = it.getOr { NewExerciseScreenResult.Fail } as? NewExerciseScreenResult.Success
            ?: return@onNavResult
        ParametersEvent.AddOrEditExercise(result).let(vm::obtainEvent)
    }

    SubscribeOnAction(
        action = action,
        navigator = navigator,
        viewModel = vm,
        confirmationResultRecipient = confirmationResultRecipient,
    )

    when (val st = state) {
        is ParametersScreenState.Loading -> {
            FullscreenLoader()
        }

        is ParametersScreenState.Data -> {
            DataContent(state = st, vm = vm)
        }
    }
}

@Composable
private fun DataContent(
    state: ParametersScreenState.Data,
    vm: ParametersViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                modifier = Modifier
                    .background(color = themeColors.black)
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.exercises_list),
                style = themeTypography.header1.copy(color = themeColors.lime)
            )
        },
        floatingActionButton = {
            TrainFab(icon = Icons.Filled.Add) {
                NewExerciseDialogScreen(requestAction = RequestAction.ADD)
                    .let(ParametersEvent::NavigateToNewExerciseScreen)
                    .let(vm::obtainEvent)
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
                        NewExerciseDialogScreen(
                            cardTitle = exerciseCard.title,
                            muscleIds = exerciseCard.muscles.map(MuscleDto::id),
                            requestAction = RequestAction.EDIT,
                        )
                            .let(ParametersEvent::NavigateToNewExerciseScreen)
                            .let(vm::obtainEvent)
                    },
                    onRemoveClick = { exerciseCard ->
                        ParametersEvent.ShowConfirmDeleteDialog(exerciseCard)
                            .let(vm::obtainEvent)
                    },
                )
            }
        }
    }
}

@Composable
private fun SubscribeOnAction(
    action: ParametersAction,
    navigator: DestinationsNavigator,
    viewModel: ParametersViewModel,
    confirmationResultRecipient: ResultRecipient<AlertConfirmationDialogDestination, Boolean>,
) {
    when (action) {
        is ParametersAction.Empty -> {
            // do nothing
        }

        is ParametersAction.ShowDeleteConfirmationDialog -> {
            ShowDeleteConfDialog(
                navigator = navigator,
                exercise = action.exercise,
                viewModel = viewModel,
                resultRecipient = confirmationResultRecipient,
            )
        }

        is ParametersAction.ShowItemIsAlreadyExistedDialog -> {
            ShowExerciseExistsDialog(
                navigator = navigator,
                exerciseTitle = action.title,
            )
        }

        is ParametersAction.OpenNewExerciseScreen -> {
            ShowNewExerciseScreen(screen = action.data, navigator = navigator)
        }
    }
}

@Composable
private fun ShowDeleteConfDialog(
    navigator: DestinationsNavigator,
    exercise: ExerciseCardDto,
    viewModel: ParametersViewModel,
    resultRecipient: ResultRecipient<AlertConfirmationDialogDestination, Boolean>,
) {
    val screenMapper = localScreenMapper.current

    resultRecipient.onNavResult {
        val result = it.getOr { false }
        if (result) {
            ParametersEvent.RemoveExercise(exercise).let(viewModel::obtainEvent)
            return@onNavResult
        }
        viewModel.obtainEvent(null)
    }

    screenMapper(
        AlertConfirmationDialogScreen(
            dialogLabel = CONFIRM_DELETE_EXERCISE_DIALOG_LABEL,
            title = exercise.title,
            message = stringResource(id = R.string.exercise_delete_message),
            positiveAction = stringResource(id = me.kovp.components.R.string.action_ok),
            negativeAction = stringResource(id = me.kovp.components.R.string.action_cancel),
        )
    )
        .let(navigator::navigate)
}

@Composable
private fun ShowExerciseExistsDialog(
    navigator: DestinationsNavigator,
    exerciseTitle: String,
) {
    val screenMapper = localScreenMapper.current

    screenMapper(
        AlertConfirmationDialogScreen(
            dialogLabel = EXERCISE_ALREADY_EXISTS_DIALOG_LABEL,
            title = stringResource(id = R.string.new_exercise_screen_title),
            message = stringResource(id = R.string.exercise_already_exists, exerciseTitle),
            positiveAction = stringResource(id = me.kovp.components.R.string.action_ok),
        )
    )
        .let(navigator::navigate)
}

@Composable
private fun ShowNewExerciseScreen(
    screen: NewExerciseDialogScreen,
    navigator: DestinationsNavigator,
) {
    val screenMapper = localScreenMapper.current

    screenMapper(screen).let(navigator::navigate)
}
