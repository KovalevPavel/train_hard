package kovp.trainhard.parameters_presentation.parameters.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kovp.trainhard.components.exercise_type.ExerciseCardVs
import kovp.trainhard.components.exercise_type.ExerciseCardVs.MuscleVs
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.models.ExerciseVo
import kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import timber.log.Timber
import trainhard.kovp.core.ResourceProvider
import java.util.UUID

class ParametersViewModel(
    private val getExercises: GetAllExercisesInteractor,
    private val removeExistingExercise: RemoveExerciseInteractor,
    private val resourceProvider: ResourceProvider,
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
                mutableEventFlow.emit(
                    ParametersEvent.ShowBottomSheetDialog(
                        dialogState = getRemoveExerciseAlert(exercise = exercise),
                    ),
                )
            },
            error = ::handleError,
        )
    }

    private fun openParametersScreen(exercise: ExerciseCardVs?) {
        viewModelScope.launch {
            mutableEventFlow.emit(
                ParametersEvent.NavigateToExerciseParams(
                    arg = ExerciseParametersArg(
                        title = exercise?.title.orEmpty(),
                        muscleIds = exercise?.muscles?.map(MuscleVs::id).orEmpty(),
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
                ExerciseVo(
                    title = exercise.title,
                    muscles = exercise.muscles.mapNotNull {
                        Muscles.getMuscleById(it.id)
                    },
                )
                    .let { removeExistingExercise(it) }
            },
            error = ::handleError,
        )
    }

    private fun subscribeOnExercisesList() {
        getExercises().onEach { list ->
            ParametersScreenState.Loading.let(::updateState)

            list.map {
                ExerciseCardVs(
                    title = it.title,
                    muscles = it.muscles.map { m ->
                        MuscleVs(
                            muscleId = m.muscleId,
                            muscleGroup = m.muscleGroup,
                        )
                    },
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
        viewModelScope.launch {
            ParametersEvent.ShowBottomSheetDialog(
                dialogState = MessageDialogState(
                    dialogId = UUID.randomUUID().toString(),
                    title = e.message.orEmpty(),
                    positiveAction = DialogState.Action(
                        resourceProvider.getString(kovp.trainhard.design_system.R.string.action_ok),
                    ),
                )
            )
                .let { mutableEventFlow.emit(it) }
        }
    }

    companion object {
        private const val CONFIRM_DELETE_EXERCISE_DIALOG_LABEL =
            "CONFIRM_DELETE_EXERCISE_DIALOG_LABEL"
    }
}
