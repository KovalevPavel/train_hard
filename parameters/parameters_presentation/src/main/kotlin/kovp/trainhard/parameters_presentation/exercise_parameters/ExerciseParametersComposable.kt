package kovp.trainhard.parameters_presentation.exercise_parameters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.muscle_groups_cloud.MuscleGroupsCloud
import kovp.trainhard.components.text_field.TrainTextField
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.di.exerciseParametersModule
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

@Composable
fun ExerciseParametersComposable(
    argument: ExerciseParametersArg,
    navController: NavController,
) {
    loadKoinModules(exerciseParametersModule(arg = argument))
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

    StateContainer(
        modifier = Modifier.background(themeColors.black),
        state = state,
    ) {
        when (it) {
            ExerciseParametersState.Loading -> {

            }

            is ExerciseParametersState.Content -> {
                ScreenData(
                    state = it,
                    handleAction = vm::handleAction,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenData(
    state: ExerciseParametersState.Content,
    handleAction: (ExerciseParametersAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(themeColors.black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        handleAction(ExerciseParametersAction.OnBackClick)
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = themeColors.white,
                        contentDescription = null,
                    )
                }
            },
            title = {},
            actions = {
                Surface(
                    shape = CircleShape,
                    onClick = {
                        handleAction(
                            ExerciseParametersAction.OnActionClick,
                        )
                    },
                    color = Color.Transparent,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        text = state.action,
                        color = themeColors.lime,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = themeColors.black,
            ),
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = state.screenTitle,
            style = themeTypography.header1,
            color = themeColors.white,
        )

        TrainTextField(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
                .padding(bottom = 8.dp)
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(MuscleGroup.entries) {
                MuscleGroupsCloud(
                    group = it,
                    initialList = state.muscleIds.mapNotNull(Muscles::getMuscleByFullId),
                ) { muscle, isSelected ->
                    handleAction(
                        ExerciseParametersAction.OnMuscleAction(
                            muscleId = muscle.id,
                            isSelected = isSelected,
                        )
                    )
                }
            }
        }
    }
}
