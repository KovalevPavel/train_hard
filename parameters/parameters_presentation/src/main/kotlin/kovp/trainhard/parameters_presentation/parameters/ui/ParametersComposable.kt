package kovp.trainhard.parameters_presentation.parameters.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.parameters_presentation.di.parametersModule
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersRoute
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersAction
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersEvent
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersScreenState
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI
import timber.log.Timber

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ParametersComposable(
    navController: NavController,
) {
    rememberKoinModules(unloadOnForgotten = false) { listOf(parametersModule) }

    val vm = koinViewModel<ParametersViewModel>()
    Timber.e("${vm.hashCode()}")
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
    handleAction: (ParametersAction) -> Unit,
) {
    StateContainer(
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
