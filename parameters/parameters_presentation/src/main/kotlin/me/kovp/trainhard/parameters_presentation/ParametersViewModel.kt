package me.kovp.trainhard.parameters_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto.MuscleDto
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.database_api.errors.EntityExistsException
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import me.kovp.trainhard.parameters_domain.InsertNewExerciseInteractor
import me.kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import me.kovp.trainhard.parameters_domain.UpdateExistingExerciseInteractor
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult.Success.ScreenAction
import timber.log.Timber

class ParametersViewModel(
    private val getExercises: GetAllExercisesInteractor,
    private val insertExercise: InsertNewExerciseInteractor,
    private val updateExercise: UpdateExistingExerciseInteractor,
    private val removeExistingExercise: RemoveExerciseInteractor,
) : BaseViewModel<ParametersScreenState>(initialState = ParametersScreenState.init) {
    val actionFlow: Flow<ParametersAction> by lazy { _actionFlow }

    private val _actionFlow = MutableSharedFlow<ParametersAction>()

    init {
        subscribeOnExercisesList()
    }

    fun obtainEvent(event: ParametersEvent?) {
        viewModelScope.launch {
            when (event) {
                is ParametersEvent.ShowConfirmDeleteDialog -> {
                    ParametersAction.ShowDeleteConfirmationDialog(exercise = event.exercise)
                        .let { _actionFlow.emit(it) }
                }

                null -> _actionFlow.emit(ParametersAction.Empty)
            }
        }
    }

    fun removeExercise(exercise: ExerciseCardDto) {
        launch(
            action = {
                Exercise(
                    title = exercise.title,
                    muscles = exercise.muscles.mapNotNull {
                        Muscles.getMuscleById(it.id)
                    },
                )
                    .let { removeExistingExercise(it) }
            },
        )
    }

    fun addOrEditExercise(exercise: NewExerciseScreenResult.Success) {
        val muscles = Muscles.allMuscles.filter { it.id in exercise.muscleIds }
        val mappedExercise = Exercise(title = exercise.title, muscles = muscles)
        when (exercise.screenAction) {
            ScreenAction.ADD -> addNewExercise(exercise = mappedExercise)
            ScreenAction.EDIT -> editExercise(exercise = mappedExercise)
        }
    }

    private fun subscribeOnExercisesList() {
        viewModelScope.launch {
            getExercises().collect { list ->
                mutableStateFlow.value.copy(isLoading = true).let { mutableStateFlow.emit(it) }

                val dtoList = list.map {
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

                ParametersScreenState(
                    items = dtoList,
                    isLoading = false,
                )
                    .let { mutableStateFlow.emit(it) }
            }
        }
    }

    private fun addNewExercise(exercise: Exercise) {
        launch(
            action = {
                insertExercise(exercise)
            },
            error = ::handleError,
        )
    }

    private fun handleError(e: Throwable) {
        Timber.e(e)
        viewModelScope.launch {
            when (e) {
                is EntityExistsException -> {
                    ParametersAction.ShowItemIsAlreadyExistedDialog(title = e.title)
                        .let { _actionFlow.emit(it) }
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
