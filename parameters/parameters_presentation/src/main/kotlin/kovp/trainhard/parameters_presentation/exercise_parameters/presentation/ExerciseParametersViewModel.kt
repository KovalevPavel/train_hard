package kovp.trainhard.parameters_presentation.exercise_parameters.presentation

import kotlinx.collections.immutable.toImmutableList
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
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import timber.log.Timber
import trainhard.kovp.core.ResourceProvider
import java.util.UUID

class ExerciseParametersViewModel(
    private val exerciseArgument: ExerciseParametersArg,
    private val exercisesApi: ExercisesApi,
    private val resourceProvider: ResourceProvider,
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
        val actionRes = if (isNewExercise) {
            kovp.trainhard.design_system.R.string.add
        } else {
            kovp.trainhard.design_system.R.string.save
        }

        val titleResId = if (isNewExercise) {
            R.string.new_exercise_screen_title
        } else {
            R.string.edit_exercise_screen_title
        }

        ExerciseParametersState.Content(
            screenTitle = resourceProvider.getString(titleResId),
            action = resourceProvider.getString(actionRes),
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

    private fun handleOnActionClick() {
        launch(
            action = {
                if (currentName.isEmpty() || musclesCloud.isEmpty()) {
                    MessageDialogState(
                        dialogId = UUID.randomUUID().toString(),
                        title = resourceProvider.getString(R.string.enter_info),
                        positiveAction = resourceProvider.getString(kovp.trainhard.design_system.R.string.action_ok)
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
        Timber.e(e)
        launch {
            when (e) {
                is EntityExistsException -> {
                    ExerciseParametersEvent.ShowMessageDialog(
                        state = MessageDialogState(
                            dialogId = EXERCISE_ALREADY_EXISTS_DIALOG_ID,
                            title = e.title,
                            positiveAction = DialogState.Action(
                                resourceProvider.getString(kovp.trainhard.design_system.R.string.action_ok),
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
                    title = resourceProvider.getString(R.string.exit_without_save),
                    positiveAction = DialogState.Action(
                        action = resourceProvider.getString(R.string.exit)
                    ),
                    negativeAction = DialogState.Action(
                        action = resourceProvider.getString(
                            resId = kovp.trainhard.design_system.R.string.action_cancel,
                        ),
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
