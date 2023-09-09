package me.kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.database_api.ExercisesApi

interface SelectNewExerciseTypeViewModel {
    val screenState: Flow<SelectExerciseScreenState>
}

class SelectNewExerciseTypeViewModelImpl(
    private val exercisesApi: ExercisesApi,
) : ViewModel(), SelectNewExerciseTypeViewModel {
    override val screenState = MutableStateFlow(SelectExerciseScreenState.init)

    init {
        viewModelScope.launch {
            val list = exercisesApi.getExercises()
            SelectExerciseScreenState(
                items = list,
                isLoading = false,
            )
                .let { screenState.emit(it) }
        }
    }
}
