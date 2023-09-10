package me.kovp.trainhard.parameters_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseDialogResult
import timber.log.Timber

interface ParametersViewModel {
    val state: StateFlow<ParametersScreenState>

    fun removeExercise(exercise: ExerciseCardDto)
    fun addOrEditExercise(exercise: NewExerciseDialogResult.Success)
    fun editExercise(exercise: NewExerciseDialogResult.Success)
}

class ParametersViewModelImpl(
    private val getExercises: GetAllExercisesInteractor,
) : ViewModel(), ParametersViewModel {
    override val state = MutableStateFlow(ParametersScreenState.init)

    init {
        subscribeOnExercisesList()
    }

    override fun removeExercise(exercise: ExerciseCardDto) {
        Timber.e("remove -> $exercise")
    }

    override fun addOrEditExercise(exercise: NewExerciseDialogResult.Success) {
        TODO("Not yet implemented")
    }

    override fun editExercise(exercise: NewExerciseDialogResult.Success) {
        Timber.e("exercise -> $exercise")
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
}
