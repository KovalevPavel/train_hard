package me.kovp.trainhard.new_training_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.new_trainig_domain.AddNewSetInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllExercisesInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllSetsInteractor
import me.kovp.trainhard.new_trainig_domain.GetMuscleGroupsInteractor
import me.kovp.trainhard.new_trainig_domain.SaveTrainingInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateSetInteractor
import me.kovp.trainhard.new_training_presentation.TrainingScreenState.SetItem
import timber.log.Timber

interface NewTrainingViewModel {
    val screenState: Flow<TrainingScreenState>

    fun addNewExercise()
    fun editSet(oldSet: SetItem)
}

class NewTrainingViewModelImpl(
    private val saveTraining: SaveTrainingInteractor,
    private val getMuscleGroups: GetMuscleGroupsInteractor,
    private val getAllExercises: GetAllExercisesInteractor,
    private val addNewSet: AddNewSetInteractor,
    private val getAllSets: GetAllSetsInteractor,
    private val updateSet: UpdateSetInteractor,
) : ViewModel(), NewTrainingViewModel {
    override var screenState: MutableStateFlow<TrainingScreenState> =
        MutableStateFlow(TrainingScreenState.init)

    private var exercises: List<Exercise> = emptyList()

    init {
        loadProgramExercises()
        viewModelScope.launch {
            getAllExercises().let { exercises = it }
            getAllSets()
                .onEach { list ->
                    screenState.value.copy(
                        items = list.map {
                            SetItem(
                                payload = it,
                                title = it.exercise.title,
                                muscleGroups = it.exercise.muscleGroups.map(MuscleGroup::toString),
                                reps = it.reps.map { (w, r) -> "${w}x$r" }
                            )
                        }
                    )
                        .let { screenState.emit(it) }
                }
                .launchIn(viewModelScope)
        }
    }

    override fun addNewExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            addNewSet(
                exercise = exercises.random(),
                reps = listOf(
                    10f to 3,
                    20f to 5,
                ),
            )
        }
    }

    override fun editSet(oldSet: SetItem) {
        val oldExercise = oldSet.payload
        viewModelScope.launch(Dispatchers.IO) {
            updateSet(oldExercise.copy(reps = oldExercise.reps + oldExercise.reps))
        }
    }

    private fun loadProgramExercises() {
        viewModelScope.launch {
            getAllExercises().joinToString("\n").let {
                Timber.e("exercises from DB:\n$it")
            }
        }
    }
}
