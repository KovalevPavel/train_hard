package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.components.train_card.CompletedExerciseCardDto
import me.kovp.trainhard.core_domain.Muscle
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
) : BaseViewModel<TrainingScreenState>(initialState = TrainingScreenState.init) {
    private val completedExercises: MutableList<CompletedExercise> = mutableListOf()

    init {
        subscribeOnSetsList()
    }

    fun addNewCompletedExercise(dialogResult: NewSetDialogResult.Success) {
        launch(
            action = {
                getExerciseById(id = dialogResult.exerciseTitle)?.let {
                    addNewCompletedSet(
                        dateString = currentDate,
                        exercise = it,
                        sets = listOf(
                            dialogResult.weight to dialogResult.reps,
                        )
                    )
                }
            }
        )
    }

    fun addOrEditSet(dialogResult: NewSetDialogResult.Success) {
        launch(
            action = {
                val completedExercise = completedExercises.firstOrNull {
                    it.id == dialogResult.id && it.exercise.title == dialogResult.exerciseTitle
                }
                    ?: return@launch

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
            }
        )
    }

    fun removeSet(setDto: CompletedExerciseCardDto, setIndex: Int) {
        launch(
            action = {
                val completedExercise = completedExercises.firstOrNull {
                    it.id == setDto.setId && it.date == setDto.setDate && it.exercise.title == setDto.exerciseTitle
                }
                    ?: return@launch

                val index = completedExercises.indexOf(completedExercise)

                if (completedExercise.sets.size == 1) {
                    removeCompletedExercise(completedExercise)
                    completedExercises.remove(completedExercise)
                    return@launch
                }

                val updateSet = completedExercise - listOf(completedExercise.sets[setIndex])

                completedExercises[index] = updateSet
                updateCompletedExercise(editedCompletedExercise = updateSet)
            }
        )
    }

    private fun subscribeOnSetsList() {
        launch(
            action = {
                getAllExercises(date = currentDate).collect { list ->
                    mutableStateFlow.value.copy(isLoading = true).let { mutableStateFlow.emit(it) }

                    completedExercises.clear()
                    list.let(completedExercises::addAll)
                    mutableStateFlow.value
                        .copy(items = list.map(::mapToCardDto), isLoading = false)
                        .let { mutableStateFlow.emit(it) }
                }
            }
        )
    }

    private fun mapToCardDto(item: CompletedExercise) = CompletedExerciseCardDto(
        setId = item.id,
        setDate = item.date,
        exerciseTitle = item.exercise.title,
        sets = item.sets,
        muscles = item.exercise.muscles.map(Muscle::id),
    )
}
