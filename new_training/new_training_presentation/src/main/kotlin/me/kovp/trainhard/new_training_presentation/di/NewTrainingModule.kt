package me.kovp.trainhard.new_training_presentation.di

import me.kovp.trainhard.new_trainig_domain.AddNewCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllSetsInteractor
import me.kovp.trainhard.new_trainig_domain.GetExerciseByIdInteractor
import me.kovp.trainhard.new_trainig_domain.RemoveCompletedExerciseInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateCompletedExerciseInteractor
import me.kovp.trainhard.new_training_presentation.TrainingViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun newTrainingModule(currentDate: String) = module {
    single { AddNewCompletedExerciseInteractor(completedExerciseApi = get()) }
    single { GetAllSetsInteractor(exercisesApi = get()) }
    single { UpdateCompletedExerciseInteractor(completedExerciseApi = get()) }
    single { GetExerciseByIdInteractor(exercisesApi = get()) }
    single { RemoveCompletedExerciseInteractor(completedExerciseApi = get()) }

    viewModel {
        TrainingViewModelImpl(
            currentDate = currentDate,
            addNewCompletedSet = get(),
            getAllSets = get(),
            updateCompletedExercise = get(),
            getExerciseById = get(),
            removeCompletedExercise = get(),
        )
    }
}