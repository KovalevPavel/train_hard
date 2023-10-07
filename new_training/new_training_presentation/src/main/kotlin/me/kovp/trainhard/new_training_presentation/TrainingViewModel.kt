package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.components.train_card.CompletedExerciseCardDto
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.minus
import me.kovp.trainhard.database_api.models.plus
import me.kovp.trainhard.new_trainig_domain.AddNewCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllCompletedExercisesInteractor
import me.kovp.trainhard.new_trainig_domain.GetExerciseByIdInteractor
import me.kovp.trainhard.new_trainig_domain.RemoveCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateCompletedExerciseInteractor
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult.DialogAction

class TrainingViewModel(
    private val currentDate: String,
    private val addNewCompletedSet: AddNewCompletedExerciseInteractor,
    private val getAllExercises: GetAllCompletedExercisesInteractor,
    private val updateCompletedExercise: UpdateCompletedExerciseInteractor,
    private val getExerciseById: GetExerciseByIdInteractor,
    private val removeCompletedExercise: RemoveCompletedExerciseInteractor,
) : BaseViewModel<TrainingScreenState, TrainingEvent, TrainingAction>(
    initialState = TrainingScreenState.Loading,
) {
    private val completedExercises: MutableList<CompletedExercise> = mutableListOf()

    init {
        subscribeOnSetsList()
    }

    override fun obtainEvent(event: TrainingEvent?) {
        launch(
            action = {
                when (event) {
                    is TrainingEvent.OnAddExerciseClick -> {
                        TrainingAction.NavigateToSelectExerciseType
                            .let { mutableActionFlow.emit(it) }
                    }

                    is TrainingEvent.NavigateToSetDialog -> {
                        TrainingAction.NavigateToEditSetDialog(data = event.dialog)
                            .let { mutableActionFlow.emit(it) }
                    }

                    is TrainingEvent.AddOrEditSet -> {
                        addOrEditSet(dialogResult = event.data)
                    }

                    is TrainingEvent.AddNewCompletedExercise -> {
                        addNewCompletedExercise(dialogResult = event.data)
                    }

                    is TrainingEvent.OnRemoveSetClick -> {
                        removeSet(
                            setDto = event.setDto,
                            setIndex = event.setIndex,
                        )
                    }

                    null -> {
                        mutableActionFlow.emit(TrainingAction.Empty)
                    }
                }
            },
        )
    }

    private fun subscribeOnSetsList() {
        launch(
            action = {
                getAllExercises(date = currentDate).collect { list ->
                    TrainingScreenState.Loading.let(mutableStateFlow::update)

                    completedExercises.clear()
                    list.let(completedExercises::addAll)
                    list.map(::mapToCardDto)
                        .let(TrainingScreenState::Data)
                        .let(mutableStateFlow::update)
                }
            }
        )
    }

    private suspend fun addNewCompletedExercise(dialogResult: NewSetDialogResult.Success) {
        getExerciseById(id = dialogResult.exerciseTitle)?.let {
            addNewCompletedSet(
                dateString = currentDate,
                exercise = it,
                sets = listOf(
                    dialogResult.weight to dialogResult.reps,
                )
            )
        }
        obtainEvent(null)
    }

    private suspend fun addOrEditSet(dialogResult: NewSetDialogResult.Success) {
        val completedExercise = completedExercises.firstOrNull {
            it.id == dialogResult.id && it.exercise.title == dialogResult.exerciseTitle
        }
            ?: return

        val updatedExercise = when (dialogResult.resultAction) {
            DialogAction.ADD_NEW -> {
                completedExercise + listOf(dialogResult.weight to dialogResult.reps)
            }

            DialogAction.EDIT_CURRENT -> {
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
        obtainEvent(null)
    }

    private suspend fun removeSet(setDto: CompletedExerciseCardDto, setIndex: Int) {
        val completedExercise = completedExercises.firstOrNull {
            it.id == setDto.setId && it.date == setDto.setDate && it.exercise.title == setDto.exerciseTitle
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
        obtainEvent(null)
    }

    private fun mapToCardDto(item: CompletedExercise) = CompletedExerciseCardDto(
        setId = item.id,
        setDate = item.date,
        exerciseTitle = item.exercise.title,
        sets = item.sets,
        muscles = item.exercise.muscles.map(Muscle::id),
    )
}
