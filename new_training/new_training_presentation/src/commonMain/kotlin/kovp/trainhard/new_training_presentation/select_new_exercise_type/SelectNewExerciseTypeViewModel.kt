package kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.ExercisesApi

class SelectNewExerciseTypeViewModel(
    exercisesApi: ExercisesApi,
    private val configHolder: ConfigHolder,
) : BaseViewModel<SelectExerciseScreenState, SelectExerciseAction, SelectExerciseEvent>(
    initialState = SelectExerciseScreenState.Loading,
) {
    init {
        exercisesApi.getExercises()
            .onEach { list ->
                list.map { vo ->
                    ExerciseVs(
                        title = vo.title,
                        muscles = vo.muscles.mapNotNull { m ->
                            configHolder.exercisesConfig.getLocalizedString(m.id)
                                ?: return@mapNotNull null
                        }
                            .joinToString(),
                    )
                }
                    .toImmutableList()
                    .let(SelectExerciseScreenState::Data).let(::updateState)
            }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: SelectExerciseAction) {
        launch {
            when (action) {
                is SelectExerciseAction.OnExerciseClick -> {
                    action.data.title
                        .let(SelectExerciseEvent::NavigateBack)
                }
            }
                .let { emitEvent(it) }
        }
    }
}
