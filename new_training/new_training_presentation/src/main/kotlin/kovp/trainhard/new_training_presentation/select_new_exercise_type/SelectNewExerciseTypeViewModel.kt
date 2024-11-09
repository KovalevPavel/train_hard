package kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kovp.trainhard.core_domain.update
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.new_training_api.NewSetDialogScreen
import kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction

class SelectNewExerciseTypeViewModel(
    private val exercisesApi: ExercisesApi,
) : BaseViewModel<SelectExerciseScreenState, SelectExerciseEvent, SelectExerciseAction>(
    initialState = SelectExerciseScreenState.Loading,
) {
    init {
        viewModelScope.launch {
            exercisesApi.getExercises().collect { list ->
                list.let(SelectExerciseScreenState::Data)
                    .let { state = it }
            }
        }
    }

    override fun handleAction(event: SelectExerciseEvent?) {
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
