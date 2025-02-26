package kovp.trainhard.new_training_presentation.screen

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kovp.trainhard.components.train_card.CompletedExerciseCardVs
import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.database_api.models.CompletedExercise
import kovp.trainhard.database_api.models.minus
import kovp.trainhard.database_api.models.plus
import kovp.trainhard.new_trainig_domain.AddNewCompletedExerciseInteractor
import kovp.trainhard.new_trainig_domain.GetAllCompletedExercisesInteractor
import kovp.trainhard.new_trainig_domain.GetExerciseByIdInteractor
import kovp.trainhard.new_trainig_domain.RemoveCompletedExerciseInteractor
import kovp.trainhard.new_trainig_domain.UpdateCompletedExerciseInteractor
import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogResult

class TrainingViewModel(
    private val currentTimestamp: Long,
    private val addNewCompletedSet: AddNewCompletedExerciseInteractor,
    private val getAllExercises: GetAllCompletedExercisesInteractor,
    private val updateCompletedExercise: UpdateCompletedExerciseInteractor,
    private val getExerciseById: GetExerciseByIdInteractor,
    private val removeCompletedExercise: RemoveCompletedExerciseInteractor,
    configHolder: ConfigHolder,
) : BaseViewModel<TrainingScreenState, TrainingAction, TrainingEvent>(
    initialState = TrainingScreenState.Loading,
) {
    val weightIncrement = configHolder.trainingConfig.weightIncrement
    private val completedExercises: MutableList<CompletedExercise> = mutableListOf()
    private val exercisesConfig = configHolder.exercisesConfig

    init {
        subscribeOnSetsList()
    }

    override fun handleAction(action: TrainingAction) {
        launch {
            when (action) {
                is TrainingAction.OnAddExerciseClick -> {
                    emitEvent(event = TrainingEvent.NavigateToSelectExerciseType)
                }

                is TrainingAction.NavigateToSetDialog -> {
                    emitEvent(event = TrainingEvent.NavigateToEditSetDialog(data = action.dialog))
                }

                is TrainingAction.AddOrEditSet -> {
                    addOrEditSet(dialogResult = action.data)
                }

                is TrainingAction.AddNewCompletedExercise -> {
                    addNewCompletedExercise(dialogResult = action.data)
                }

                is TrainingAction.OnRemoveSetClick -> {
                    removeSet(
                        setDto = action.setDto,
                        setIndex = action.setIndex,
                    )
                }
            }
        }
    }

    private fun subscribeOnSetsList() {
        getAllExercises(timestamp = currentTimestamp).onEach { list ->
            TrainingScreenState.Loading.let(::updateState)

            completedExercises.clear()
            list.let(completedExercises::addAll)
            list.map(::mapToCardVs)
                .toImmutableList()
                .let(TrainingScreenState::Data)
                .let(::updateState)
        }
            .launchIn(viewModelScope)
    }

    private suspend fun addNewCompletedExercise(dialogResult: EditSetDialogResult.Success) {
        getExerciseById(id = dialogResult.exerciseTitle)?.let {
            addNewCompletedSet(
                timestamp = currentTimestamp,
                exercise = it,
                sets = listOf(
                    dialogResult.weight to dialogResult.reps,
                )
            )
        }
    }

    private suspend fun addOrEditSet(dialogResult: EditSetDialogResult.Success) {
        val completedExercise = completedExercises.firstOrNull {
            it.id == dialogResult.id && it.exercise.title == dialogResult.exerciseTitle
        }
            ?: return

        val updatedExercise = when (dialogResult.setId) {
            null -> {
                completedExercise + listOf(dialogResult.weight to dialogResult.reps)
            }

            else -> {
                val modifiedReps = completedExercise.sets
                    .mapIndexed { i, p ->
                        if (i.toLong() == dialogResult.setId) {
                            dialogResult.weight to dialogResult.reps
                        } else {
                            p
                        }
                    }
                completedExercise.copy(sets = modifiedReps)
            }
        }

        updateCompletedExercise(editedCompletedExercise = updatedExercise)
    }

    private suspend fun removeSet(setDto: CompletedExerciseCardVs, setIndex: Int) {
        val completedExercise = completedExercises.firstOrNull {
            it.id == setDto.setId &&
                    it.dayTimestamp == setDto.timestamp &&
                    it.exercise.title == setDto.exerciseTitle
        }
            ?: return

        val index = completedExercises.indexOf(completedExercise)

        if (completedExercise.sets.size == 1) {
            removeCompletedExercise(completedExercise)
            completedExercises.remove(completedExercise)
            return
        }

        val updateSet = completedExercise - listOf(completedExercise.sets[setIndex])

        completedExercises[index] = updateSet
        updateCompletedExercise(editedCompletedExercise = updateSet)
    }

    private fun mapToCardVs(item: CompletedExercise) = CompletedExerciseCardVs(
        setId = item.id,
        timestamp = item.dayTimestamp,
        exerciseTitle = item.exercise.title,
        sets = item.sets,
        muscles = item.exercise.muscles.mapNotNull { m ->
            exercisesConfig.getLocalizedString(m.id) ?: return@mapNotNull null
        }
            .joinToString(),
    )
}
