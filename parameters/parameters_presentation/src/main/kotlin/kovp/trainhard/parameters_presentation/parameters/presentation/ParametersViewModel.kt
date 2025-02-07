package kovp.trainhard.parameters_presentation.parameters.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kovp.trainhard.components.exercise_type.ExerciseCardVs
import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import timber.log.Timber
import trainhard.kovp.core.ResourceProvider
import java.util.UUID

class ParametersViewModel(
    private val exercisesApi: ExercisesApi,
    private val resourceProvider: ResourceProvider,
    private val configHolder: ConfigHolder,
) : BaseViewModel<ParametersScreenState, ParametersAction, ParametersEvent>(
    initialState = ParametersScreenState.Loading,
) {
    init {
        subscribeOnExercisesList()
    }

    override fun handleAction(action: ParametersAction) {
        when (action) {
            is ParametersAction.OnDeleteExerciseClicked -> {
                tryToDeleteExercise(exercise = action.exercise)
            }

            is ParametersAction.OnDialogPositiveClick -> {
                handleDialogPositiveAction(
                    dialogId = action.state?.dialogId,
                    payload = action.state?.payload,
                )
            }

            is ParametersAction.OnAddButtonClick -> {
                openParametersScreen(exercise = null)
            }

            is ParametersAction.OnExerciseClick -> {
                openParametersScreen(exercise = action.exercise)
            }
        }
    }

    private fun tryToDeleteExercise(exercise: ExerciseCardVs) {
        launch(
            action = {
                emitEvent(
                    event = ParametersEvent.ShowBottomSheetDialog(
                        dialogState = getRemoveExerciseAlert(exercise = exercise),
                    ),
                )
            },
            error = ::handleError,
        )
    }

    private fun openParametersScreen(exercise: ExerciseCardVs?) {
        launch {
            val muscleIds = exercisesApi.getExerciseById(exercise?.title.orEmpty())
                ?.muscles
                ?.map(ExercisesConfig.MuscleVo::id)
                .orEmpty()

            emitEvent(
                event = ParametersEvent.NavigateToExerciseParams(
                    arg = ExerciseParametersArg(
                        title = exercise?.title.orEmpty(),
                        muscleIds = muscleIds,
                    ),
                )
            )
        }
    }

    private fun getRemoveExerciseAlert(exercise: ExerciseCardVs): DialogState {
        return MessageDialogState(
            dialogId = CONFIRM_DELETE_EXERCISE_DIALOG_LABEL,
            title = exercise.title,
            message = resourceProvider.getString(R.string.exercise_delete_message),
            positiveAction = DialogState.Action(
                action = resourceProvider.getString(
                    kovp.trainhard.design_system.R.string.action_ok,
                ),
            ),
            negativeAction = DialogState.Action(
                action = resourceProvider.getString(
                    kovp.trainhard.design_system.R.string.action_cancel,
                ),
            ),
            payload = exercise,
        )
    }

    private fun removeExercise(exercise: ExerciseCardVs) {
        launch(
            action = {
                exercisesApi.removeExercise(exercise.title)
            },
            error = ::handleError,
        )
    }

    private fun subscribeOnExercisesList() {
        exercisesApi.getExercises().onEach { list ->
            ParametersScreenState.Loading.let(::updateState)

            list.map {
                ExerciseCardVs(
                    title = it.title,
                    muscles = it.muscles.mapNotNull { m ->
                        configHolder.exercisesConfig.getLocalizedString(m.id)
                            ?: return@mapNotNull null
                    }
                        .joinToString()
                        .replaceFirstChar(Char::uppercaseChar)
                )
            }
                .toImmutableList()
                .let(ParametersScreenState::Data)
                .let(::updateState)
        }
            .launchIn(viewModelScope)
    }

    private fun handleDialogPositiveAction(dialogId: String?, payload: Any?) {
        when (dialogId) {
            CONFIRM_DELETE_EXERCISE_DIALOG_LABEL -> {
                (payload as? ExerciseCardVs)?.let { removeExercise(it) }
            }
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        launch {
            ParametersEvent.ShowBottomSheetDialog(
                dialogState = MessageDialogState(
                    dialogId = UUID.randomUUID().toString(),
                    title = e.message.orEmpty(),
                    positiveAction = DialogState.Action(
                        resourceProvider.getString(kovp.trainhard.design_system.R.string.action_ok),
                    ),
                )
            )
                .let { emitEvent(it) }
        }
    }

    companion object {
        private const val CONFIRM_DELETE_EXERCISE_DIALOG_LABEL =
            "CONFIRM_DELETE_EXERCISE_DIALOG_LABEL"
    }
}
