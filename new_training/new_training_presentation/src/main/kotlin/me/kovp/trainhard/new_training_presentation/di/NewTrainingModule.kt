package me.kovp.trainhard.new_training_presentation.di

import me.kovp.trainhard.new_trainig_domain.AddNewCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllCompletedExercisesInteractor
import me.kovp.trainhard.new_trainig_domain.GetExerciseByIdInteractor
import me.kovp.trainhard.new_trainig_domain.RemoveCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateCompletedExerciseInteractor
import me.kovp.trainhard.new_training_presentation.TrainingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun newTrainingModule(currentTimestamp: Long) = module {
    single { AddNewCompletedExerciseInteractor(completedExerciseApi = get()) }
    single { GetAllCompletedExercisesInteractor(exercisesApi = get()) }
    single { UpdateCompletedExerciseInteractor(completedExerciseApi = get()) }
    single { GetExerciseByIdInteractor(exercisesApi = get()) }
    single { RemoveCompletedExerciseInteractor(completedExerciseApi = get()) }

    viewModel {
        TrainingViewModel(
            currentTimestamp = currentTimestamp,
            addNewCompletedSet = get(),
            getAllExercises = get(),
            updateCompletedExercise = get(),
            getExerciseById = get(),
            removeCompletedExercise = get(),
        )
    }
}
