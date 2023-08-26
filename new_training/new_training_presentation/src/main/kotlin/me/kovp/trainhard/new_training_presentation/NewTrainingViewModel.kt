package me.kovp.trainhard.new_training_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.new_trainig_domain.GetAllExercisesInteractor
import me.kovp.trainhard.new_trainig_domain.GetMuscleGroupsInteractor
import me.kovp.trainhard.new_trainig_domain.SaveTrainingInteractor
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask

interface NewTrainingViewModel {
    val screenState: Flow<TrainingScreenState>
}

class NewTrainingViewModelImpl(
    private val saveTraining: SaveTrainingInteractor,
    private val getMuscleGroups: GetMuscleGroupsInteractor,
    private val getAllExercises: GetAllExercisesInteractor,
) : ViewModel(), NewTrainingViewModel {
    override var screenState: MutableSharedFlow<TrainingScreenState> = MutableSharedFlow()

    private val timer = Timer()

    private val timeTrainingStarted = System.currentTimeMillis()

    private val timeElapsed: Long
        get() = System.currentTimeMillis() - timeTrainingStarted

    private val task = object : TimerTask() {
        override fun run() {
            viewModelScope.launch {
                screenState.emit(
                    value = TrainingScreenState(timeElapsed)
                )
            }
        }
    }

    init {
        timer.schedule(task, 0L, 1000L)
        loadProgramExercises()
    }

    private fun loadProgramExercises() {
        viewModelScope.launch {
            getAllExercises().joinToString("\n").let {
                Timber.e("exercises from DB:\n$it")
            }
        }
    }
}
