package me.kovp.trainhard.parameters_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import me.kovp.trainhard.parameters_domain.InsertNewExerciseInteractor
import me.kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import me.kovp.trainhard.parameters_domain.UpdateExistingExerciseInteractor
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult.Success.ScreenAction

interface ParametersViewModel {
    val state: StateFlow<ParametersScreenState>

    fun removeExercise(exercise: ExerciseCardDto)
    fun addOrEditExercise(exercise: NewExerciseScreenResult.Success)
}

class ParametersViewModelImpl(
    private val getExercises: GetAllExercisesInteractor,
    private val insertExercise: InsertNewExerciseInteractor,
    private val updateExercise: UpdateExistingExerciseInteractor,
    private val removeExistingExercise: RemoveExerciseInteractor,
) : ViewModel(), ParametersViewModel {
    override val state = MutableStateFlow(ParametersScreenState.init)

    init {
        subscribeOnExercisesList()
    }

    override fun removeExercise(exercise: ExerciseCardDto) {
        Exercise(
            title = exercise.title,
            muscles = exercise.muscles,
        )
            .let { deleteExercise(it) }
    }

    override fun addOrEditExercise(exercise: NewExerciseScreenResult.Success) {
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
                val dtoList = list.map {
                    ExerciseCardDto(
                        title = it.title,
                        muscles = it.muscles,
                    )
                }

                ParametersScreenState(
                    items = dtoList,
                )
                    .let {
                        state.emit(it)
                    }
            }
        }
    }

    private fun addNewExercise(exercise: Exercise) {
        viewModelScope.launch {
            insertExercise(exercise)
        }
    }

    private fun editExercise(exercise: Exercise) {
        viewModelScope.launch {
            updateExercise(exercise)
        }
    }

    private fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            removeExistingExercise(exercise)
        }
    }
}
