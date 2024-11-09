package kovp.trainhard.parameters_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kovp.trainhard.components.exercise_type.ExerciseCardDto
import kovp.trainhard.components.exercise_type.ExerciseCardDto.MuscleDto
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.errors.EntityExistsException
import kovp.trainhard.database_api.models.Exercise
import kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import kovp.trainhard.parameters_domain.InsertNewExerciseInteractor
import kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import kovp.trainhard.parameters_domain.UpdateExistingExerciseInteractor
import kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult
import kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult.Success.ScreenAction
import timber.log.Timber

class ParametersViewModel(
    private val getExercises: GetAllExercisesInteractor,
    private val insertExercise: InsertNewExerciseInteractor,
    private val updateExercise: UpdateExistingExerciseInteractor,
    private val removeExistingExercise: RemoveExerciseInteractor,
) : BaseViewModel<ParametersScreenState, ParametersEvent, ParametersAction>(
    initialState = ParametersScreenState.Loading,
) {
    init {
        subscribeOnExercisesList()
    }

    override fun handleAction(event: ParametersEvent?) {
        launch(
            action = {
                when (event) {
                    is ParametersEvent.ShowConfirmDeleteDialog -> {
                        ParametersAction.ShowDeleteConfirmationDialog(exercise = event.exercise)
                            .let { mutableActionFlow.emit(it) }
                    }

//                    is ParametersEvent.NavigateToNewExerciseScreen -> {
//                        ParametersAction.OpenNewExerciseScreen(data = event.data)
//                            .let { mutableActionFlow.emit(it) }
//                    }

                    is ParametersEvent.RemoveExercise -> {
                        removeExercise(exercise = event.data)
                    }

                    is ParametersEvent.AddOrEditExercise -> {
                        addOrEditExercise(exercise = event.result)
                    }

                    null -> {
                        mutableActionFlow.emit(ParametersAction.Empty)
                    }
                }
            },
            error = ::handleError,
        )
    }

    private suspend fun removeExercise(exercise: ExerciseCardDto) {
        Exercise(
            title = exercise.title,
            muscles = exercise.muscles.mapNotNull {
                Muscles.getMuscleById(it.id)
            },
        )
            .let { removeExistingExercise(it) }
        handleAction(null)
    }

    private suspend fun addOrEditExercise(exercise: NewExerciseScreenResult.Success) {
        val muscles = Muscles.allMuscles.filter { it.id in exercise.muscleIds }
        val mappedExercise = Exercise(title = exercise.title, muscles = muscles)
        when (exercise.screenAction) {
            ScreenAction.ADD -> addNewExercise(exercise = mappedExercise)
            ScreenAction.EDIT -> editExercise(exercise = mappedExercise)
        }
        handleAction(null)
    }

    private fun subscribeOnExercisesList() {
        viewModelScope.launch {
            getExercises().collect { list ->
                state = ParametersScreenState.Loading

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
                    .let(ParametersScreenState::Data)
                    .let { state = it }
            }
        }
    }

    private suspend fun addNewExercise(exercise: Exercise) {
        insertExercise(exercise)
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            when (e) {
                is EntityExistsException -> {
                    ParametersAction.ShowItemIsAlreadyExistedDialog(title = e.title)
                        .let { mutableActionFlow.emit(it) }
                }
            }
        }
    }

    private fun editExercise(exercise: Exercise) {
        launch(
            action = { updateExercise(exercise) },
        )
    }

    companion object {
        const val EXERCISE_ALREADY_EXISTS_DIALOG_LABEL = "EXERCISE_ALREADY_EXISTS_DIALOG_LABEL"
        const val CONFIRM_DELETE_EXERCISE_DIALOG_LABEL = "CONFIRM_DELETE_EXERCISE_DIALOG_LABEL"
    }
}
