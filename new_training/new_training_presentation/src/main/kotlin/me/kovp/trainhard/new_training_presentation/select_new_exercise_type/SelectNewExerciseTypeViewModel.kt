package me.kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.new_training_api.NewSetDialogScreen
import me.kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction

class SelectNewExerciseTypeViewModel(
    private val exercisesApi: ExercisesApi,
) : BaseViewModel<SelectExerciseScreenState, SelectExerciseEvent, SelectExerciseAction>(
    initialState = SelectExerciseScreenState.Loading,
) {
    init {
        viewModelScope.launch {
            exercisesApi.getExercises().collect { list ->
                list.let(SelectExerciseScreenState::Data)
                    .let(mutableStateFlow::update)
            }
        }
    }

    override fun obtainEvent(event: SelectExerciseEvent?) {
        viewModelScope.launch {
            when (event) {
                is SelectExerciseEvent.OnExerciseClick -> {
                    NewSetDialogScreen(
                        exerciseTitle = event.data.title,
                        requestAction = RequestAction.ADD,
                    )
                        .let(SelectExerciseAction::NavigateToNewSetDialog)
                }

                null -> SelectExerciseAction.Empty
            }
                .let { mutableActionFlow.emit(it) }
        }
    }
}
