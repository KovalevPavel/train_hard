package me.kovp.trainhard.new_training_presentation.di

import me.kovp.trainhard.new_trainig_domain.AddNewSetInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllExercisesInteractor
import me.kovp.trainhard.new_trainig_domain.GetAllSetsInteractor
import me.kovp.trainhard.new_trainig_domain.GetMuscleGroupsInteractor
import me.kovp.trainhard.new_trainig_domain.SaveTrainingInteractor
import me.kovp.trainhard.new_trainig_domain.UpdateSetInteractor
import me.kovp.trainhard.new_training_presentation.NewTrainingViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newTrainingModule = module {
    single { SaveTrainingInteractor() }
    single { GetMuscleGroupsInteractor(muscleGroupsApi = get()) }
    single { GetAllExercisesInteractor(exercisesApi = get()) }
    single { AddNewSetInteractor(completedSetsApi = get()) }
    single { GetAllSetsInteractor(exercisesApi = get()) }
    single { UpdateSetInteractor(completedSetsApi = get()) }

    viewModel {
        NewTrainingViewModelImpl(
            saveTraining = get(),
            getMuscleGroups = get(),
            getAllExercises = get(),
            addNewSet = get(),
            getAllSets = get(),
            updateSet = get(),
        )
    }
}
