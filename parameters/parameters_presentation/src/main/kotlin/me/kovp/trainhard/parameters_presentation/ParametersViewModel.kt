package me.kovp.trainhard.parameters_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.core_presentation.BaseViewModel
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

    init {
        subscribeOnExercisesList()
    }

    fun removeExercise(exercise: ExerciseCardDto) {
        Exercise(
            title = exercise.title,
            muscles = exercise.muscles,
        )
            .let { deleteExercise(it) }
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
                        muscles = it.muscles,
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
            error = {
                Timber.e("form onError $it")
            }
        )
    }

    private fun editExercise(exercise: Exercise) {
        launch(
            action = { updateExercise(exercise) },
        )
    }

    private fun deleteExercise(exercise: Exercise) {
        launch(
            action = { removeExistingExercise(exercise) },
        )
    }
}
