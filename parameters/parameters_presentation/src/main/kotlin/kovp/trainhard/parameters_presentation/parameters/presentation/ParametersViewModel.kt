package kovp.trainhard.parameters_presentation.parameters.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kovp.trainhard.components.exercise_type.ExerciseCardDto
import kovp.trainhard.components.exercise_type.ExerciseCardDto.MuscleDto
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
        launch(
            action = {
                when (action) {
                    is ParametersAction.OnDeleteExerciseClicked -> {
                        mutableEventFlow.emit(
                            ParametersEvent.ShowBottomSheetDialog(
                                dialogState = getRemoveExerciseAlert(exercise = action.exercise),
                            ),
                        )
                    }

                    is ParametersAction.OnDialogPositiveClick -> {
                        handleDialogPositiveAction(
                            dialogId = action.state?.dialogId,
                            payload = action.state?.payload,
                        )
                    }

                    is ParametersAction.OnAddButtonClick -> {
                        mutableEventFlow.emit(
                            ParametersEvent.NavigateToExerciseParams(
                                arg = ExerciseParametersArg(
                                    title = "",
                                    muscleIds = emptyList(),
                                )
                            ),
                        )
                    }

                    is ParametersAction.OnExerciseClick -> {
                        mutableEventFlow.emit(
                            ParametersEvent.NavigateToExerciseParams(
                                arg = ExerciseParametersArg(
                                    title = action.exercise.title,
                                    muscleIds = action.exercise.muscles.map(MuscleDto::id),
                                )
                            )
                        )
                    }
                }
            },
            error = ::handleError,
        )
    }

    private fun getRemoveExerciseAlert(exercise: ExerciseCardDto): DialogState {
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

    private suspend fun removeExercise(exercise: ExerciseCardDto) {
        ExerciseVo(
            title = exercise.title,
            muscles = exercise.muscles.mapNotNull {
                Muscles.getMuscleById(it.id)
            },
        )
            .let { removeExistingExercise(it) }
    }

    private fun subscribeOnExercisesList() {
        viewModelScope.launch {
            getExercises().collect { list ->
                ParametersScreenState.Loading.let(::updateState)

                list.map {
                    ExerciseCardDto(
                        title = it.title,
                        muscles = it.muscles.map { m ->
                            MuscleDto(
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
        }
    }

    private suspend fun handleDialogPositiveAction(dialogId: String?, payload: Any?) {
        when (dialogId) {
            CONFIRM_DELETE_EXERCISE_DIALOG_LABEL -> {
                (payload as? ExerciseCardDto)?.let { removeExercise(it) }
            }
        }
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            ParametersEvent.ShowBottomSheetDialog(
                dialogState = MessageDialogState(
                    dialogId = UUID.randomUUID().toString(),
                    title = "e.title",
                    positiveAction = DialogState.Action(
                        resourceProvider.getString(kovp.trainhard.design_system.R.string.action_ok),
                    ),
                )
            )
                .let { mutableEventFlow.emit(it) }
        }
    }

    companion object {
        private const val CONFIRM_DELETE_EXERCISE_DIALOG_LABEL = "CONFIRM_DELETE_EXERCISE_DIALOG_LABEL"
    }
}
