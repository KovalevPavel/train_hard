package kovp.trainhard.parameters_presentation.exercise_parameters.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kovp.trainhard.components.PublicResources
import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_core.getMuscleByFullId
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.errors.EntityExistsException
import kovp.trainhard.database_api.models.ExerciseVo
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import org.jetbrains.compose.resources.getString
import trainhard.parameters_presentation.generated.resources.Res
import trainhard.parameters_presentation.generated.resources.edit_exercise_screen_title
import trainhard.parameters_presentation.generated.resources.enter_info
import trainhard.parameters_presentation.generated.resources.exit
import trainhard.parameters_presentation.generated.resources.exit_without_save
import trainhard.parameters_presentation.generated.resources.new_exercise_screen_title
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseParametersViewModel(
    private val exerciseArgument: ExerciseParametersArg,
    private val exercisesApi: ExercisesApi,
    configHolder: ConfigHolder,
) : BaseViewModel<ExerciseParametersState, ExerciseParametersAction, ExerciseParametersEvent>(
    initialState = ExerciseParametersState.Loading,
) {
    private val exercisesConfig = configHolder.exercisesConfig
    private val isNewExercise = exerciseArgument == ExerciseParametersArg.empty

    private val initExercise = exerciseArgument.let {
        ExerciseVo(
            title = it.title,
            muscles = it.muscleIds.mapNotNull(exercisesConfig.muscles::getMuscleByFullId)
                .sortedBy(ExercisesConfig.MuscleVo::id),
        )
    }

    private var currentName = initExercise.title
    private val musclesCloud = buildList { exerciseArgument.muscleIds.let(::addAll) }
        .toMutableList()

    private val currentExercise: ExerciseVo
        get() {
            return ExerciseVo(
                title = currentName,
                muscles = musclesCloud.mapNotNull(exercisesConfig.muscles::getMuscleByFullId)
                    .sortedBy(ExercisesConfig.MuscleVo::id),
            )
        }

    init {
        updateState()
    }

    override fun handleAction(action: ExerciseParametersAction) {
        when (action) {
            is ExerciseParametersAction.OnNameChanged -> {
                currentName = action.name
                updateState()
            }

            is ExerciseParametersAction.OnMuscleAction -> {
                if (action.isSelected) {
                    musclesCloud.add(action.muscleId)
                } else {
                    musclesCloud.remove(action.muscleId)
                }

                updateState()
            }

            is ExerciseParametersAction.OnActionClick -> {
                handleOnActionClick()
            }

            is ExerciseParametersAction.OnBackClick -> {
                tryNavigateBack()
            }

            is ExerciseParametersAction.OnDialogPositiveClick -> {
                handleDialogPositiveAction(dialogId = action.dialogId)
            }
        }
    }

    private fun updateState() {
        viewModelScope.launch {
            val actionRes = if (isNewExercise) {
                PublicResources.String.add
            } else {
                PublicResources.String.save
            }

            val titleResId = if (isNewExercise) {
                Res.string.new_exercise_screen_title
            } else {
                Res.string.edit_exercise_screen_title
            }

            ExerciseParametersState.Content(
                screenTitle = getString(titleResId),
                action = getString(actionRes),
                muscleName = currentName,
                muscleGroups = MuscleGroup.entries.map { g ->
                    ExerciseParametersState.MuscleGroupVs(
                        title = exercisesConfig.muscleGroups[g].orEmpty(),
                        muscles = exercisesConfig.muscles
                            .filter { it.muscleGroup == g }
                            .map {
                                ExerciseParametersState.MuscleVs(
                                    id = it.id,
                                    title = it.localizedString,
                                    isSelected = it.id in musclesCloud,
                                )
                            }
                            .toImmutableList()
                    )
                }
                    .toImmutableList(),
            )
                .let(::updateState)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun handleOnActionClick() {
        launch(
            action = {
                if (currentName.isEmpty() || musclesCloud.isEmpty()) {
                    MessageDialogState(
                        dialogId = Uuid.random().toString(),
                        title = getString(Res.string.enter_info),
                        positiveAction = getString(PublicResources.String.action_ok)
                            .let(DialogState::Action),
                    )
                        .let(ExerciseParametersEvent::ShowMessageDialog)
                        .let { emitEvent(it) }

                    return@launch
                }
                addOrEditExercise()
                navigateBack()
            },
            error = ::handleError,
        )
    }

    private suspend fun addOrEditExercise() {
        when {
            currentExercise == initExercise -> return
            isNewExercise -> exercisesApi.addNewExercise(currentExercise)
            else -> exercisesApi.updateExistingExercise(currentExercise)
        }
    }

    private fun handleError(e: Throwable) {
        println(e)
        launch {
            when (e) {
                is EntityExistsException -> {
                    ExerciseParametersEvent.ShowMessageDialog(
                        state = MessageDialogState(
                            dialogId = EXERCISE_ALREADY_EXISTS_DIALOG_ID,
                            title = e.title,
                            positiveAction = DialogState.Action(
                                getString(PublicResources.String.action_ok),
                            ),
                        ),
                    )
                        .let { emitEvent(it) }
                }
            }
        }
    }

    private fun tryNavigateBack() {
        launch {
            if (currentExercise == initExercise) {
                navigateBack()
            } else {
                MessageDialogState(
                    dialogId = EXIT_DIALOG_ID,
                    title = getString(Res.string.exit_without_save),
                    positiveAction = DialogState.Action(
                        action = getString(Res.string.exit),
                    ),
                    negativeAction = DialogState.Action(
                        action = getString(PublicResources.String.action_cancel),
                    ),
                )
                    .let(ExerciseParametersEvent::ShowMessageDialog)
                    .let { emitEvent(it) }
            }
        }
    }

    private suspend fun navigateBack() {
        emitEvent(event = ExerciseParametersEvent.NavigateBack)
    }

    private fun handleDialogPositiveAction(dialogId: String) {
        launch {
            when (dialogId) {
                EXIT_DIALOG_ID -> emitEvent(event = ExerciseParametersEvent.NavigateBack)
            }
        }
    }

    companion object {
        private const val EXIT_DIALOG_ID = "EXIT_DIALOG_ID"
        private const val EXERCISE_ALREADY_EXISTS_DIALOG_ID = "EXERCISE_ALREADY_EXISTS_DIALOG_ID"
    }
}
