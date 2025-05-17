package kovp.trainhard.parameters_presentation.parameters.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.di.parametersModule
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersRoute
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersAction
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersEvent
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersScreenState
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ParametersComposable(
    navController: NavController,
) {
    rememberKoinModules(unloadOnForgotten = false) { listOf(parametersModule) }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    state: ParametersScreenState,
    handleAction: (ParametersAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = themeColors.black,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.exercises_list),
                        style = themeTypography.header1,
                        color = themeColors.lime,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black),
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state is ParametersScreenState.Data,
                enter = scaleIn(),
            ) {
                TrainFab(
                    modifier = Modifier,
                    icon = Icons.Filled.Add) {
                    handleAction(ParametersAction.OnAddButtonClick)
                }
            }
        },
    ) { paddings ->
        StateContainer(
            modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            state = state,
        ) {
            when (state) {
                is ParametersScreenState.Loading -> {
                    FullscreenLoader()
                }

                is ParametersScreenState.Data -> {
                    DataContent(
                        state = state,
                        handleAction = handleAction,
                    )
                }
            }
        }
    }
}
