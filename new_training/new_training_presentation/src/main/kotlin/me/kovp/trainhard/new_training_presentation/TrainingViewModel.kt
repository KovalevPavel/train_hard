package me.kovp.trainhard.new_training_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.components.train_card.CompletedExerciseCardDto
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.minus
import me.kovp.trainhard.database_api.models.plus
import me.kovp.trainhard.new_trainig_domain.AddNewCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllSetsInteractor
import me.kovp.trainhard.new_trainig_domain.GetExerciseByIdInteractor
import me.kovp.trainhard.new_trainig_domain.RemoveCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateCompletedExerciseInteractor
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult.DialogAction

interface TrainingViewModel {
    val screenState: Flow<TrainingScreenState>

    fun addNewCompletedExercise(dialogResult: NewSetDialogResult.Success)
    fun addOrEditSet(dialogResult: NewSetDialogResult.Success)
    fun removeSet(setDto: CompletedExerciseCardDto, setIndex: Int)
}

class TrainingViewModelImpl(
    private val currentDate: String,
    private val addNewCompletedSet: AddNewCompletedExerciseInteractor,
    private val getAllSets: GetAllSetsInteractor,
    private val updateCompletedExercise: UpdateCompletedExerciseInteractor,
    private val getExerciseById: GetExerciseByIdInteractor,
    private val removeCompletedExercise: RemoveCompletedExerciseInteractor,
) : ViewModel(), TrainingViewModel {
    override var screenState: MutableStateFlow<TrainingScreenState> =
        MutableStateFlow(TrainingScreenState.init)

    private val completedExercises: MutableList<CompletedExercise> = mutableListOf()

    init {
        subscribeOnSetsList()
    }

    override fun addNewCompletedExercise(dialogResult: NewSetDialogResult.Success) {
        viewModelScope.launch {
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
    }

    override fun addOrEditSet(dialogResult: NewSetDialogResult.Success) {
        viewModelScope.launch {
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
    }

    override fun removeSet(setDto: CompletedExerciseCardDto, setIndex: Int) {
        viewModelScope.launch {
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
    }

    private fun subscribeOnSetsList() {
        viewModelScope.launch {
            getAllSets(date = currentDate).collect { list ->
                completedExercises.clear()
                list.let(completedExercises::addAll)
                screenState.value
                    .copy(items = list.map(::mapToCardDto))
                    .let { screenState.emit(it) }
            }
        }
    }

    private fun mapToCardDto(item: CompletedExercise) = CompletedExerciseCardDto(
        setId = item.id,
        setDate = item.date,
        exerciseTitle = item.exercise.title,
        sets = item.sets,
        muscleGroups = item.exercise.muscleGroups.map(MuscleGroup::getGroupId),
    )
}
