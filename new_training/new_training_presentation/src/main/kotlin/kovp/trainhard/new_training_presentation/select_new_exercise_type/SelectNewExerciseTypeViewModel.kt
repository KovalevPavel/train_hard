package kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.new_training_presentation.NewSetDialogScreen

class SelectNewExerciseTypeViewModel(
    private val exercisesApi: ExercisesApi,
) : BaseViewModel<SelectExerciseScreenState, SelectExerciseAction, SelectExerciseEvent>(
    initialState = SelectExerciseScreenState.Loading,
) {
    init {
        viewModelScope.launch {
            exercisesApi.getExercises().collect { list ->
                list.let(SelectExerciseScreenState::Data)
                    .let(::updateState)
            }
        }
    }

    override fun handleAction(action: SelectExerciseAction) {
        viewModelScope.launch {
            when (action) {
                is SelectExerciseAction.OnExerciseClick -> {
                    NewSetDialogScreen(
                        exerciseTitle = action.data.title,
                        requestAction = NewSetDialogScreen.RequestAction.ADD,
                    )
                        .let(SelectExerciseEvent::NavigateToNewSetDialog)
                }
            }
                .let { mutableEventFlow.emit(it) }
        }
    }
}
