package kovp.trainhard.parameters_presentation.exercise_parameters.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kovp.trainhard.components.text_field.TrainTextField
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.di.exerciseParametersModule
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersAction
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersEvent
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersState
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersViewModel
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ExerciseParametersComposable(
    argument: ExerciseParametersArg,
    navController: NavController,
) {
    rememberKoinModules { listOf(exerciseParametersModule(arg = argument)) }

    val vm = koinViewModel<ExerciseParametersViewModel>()
    val state by vm.state.collectAsState()
    var isDialogVisible by remember { mutableStateOf(false) }
    var dialogState: DialogState? by remember { mutableStateOf(null) }

    if (isDialogVisible) {
        BottomSheetDialog(
            state = dialogState,
            onPositiveClick = {
                dialogState?.dialogId
                    ?.let(ExerciseParametersAction::OnDialogPositiveClick)
                    ?.let(vm::handleAction)
                isDialogVisible = false
            },
            onNegativeClick = { isDialogVisible = false },
            onCancel = { isDialogVisible = false },
        )
    }

    BackHandler { ExerciseParametersAction.OnBackClick.let(vm::handleAction) }

    SubscribeOnEvents(eventFlow = vm.eventFlow) { event ->
        when (event) {
            is ExerciseParametersEvent.NavigateBack -> {
                navController.navigateUp()
            }

            is ExerciseParametersEvent.ShowMessageDialog -> {
                dialogState = event.state
                isDialogVisible = true
            }
        }
    }

    ScreenContent(
        state = state,
        handleAction = vm::handleAction,
    )
}

@Composable
private fun ScreenContent(
    state: ExerciseParametersState,
    handleAction: (ExerciseParametersAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = themeColors.black,
        topBar = {
            ParamsTopAppBar(
                title = state.screenTitle,
                action = state.action,
                handleAction = handleAction,
            )
        }
    ) { paddings ->
        StateContainer(
            modifier = Modifier
                .padding(top = paddings.calculateTopPadding())
                .fillMaxSize(),
            state = state,
        ) { st ->
            when (st) {
                ExerciseParametersState.Loading -> {
                    // Ничего не делаем. Если появятся запросы к БД, надо поставить лоадер
                }

                is ExerciseParametersState.Content -> {
                    ScreenData(
                        state = st,
                        handleAction = handleAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenData(
    state: ExerciseParametersState.Content,
    handleAction: (ExerciseParametersAction) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TrainTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            value = state.muscleName,
            hint = stringResource(id = R.string.enter_exercise_hint),
            onValueChanged = {
                handleAction(ExerciseParametersAction.OnNameChanged(it))
            },
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(id = R.string.select_muscle_groups),
            style = themeTypography.body1,
            color = themeColors.white,
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(state.muscleGroups) {
                MuscleGroupsCloud(
                    title = it.title,
                    initialList = it.muscles,
                ) { muscleId, isSelected ->
                    handleAction(
                        ExerciseParametersAction.OnMuscleAction(
                            muscleId = muscleId,
                            isSelected = isSelected,
                        )
                    )
                }
            }
        }
    }
}
