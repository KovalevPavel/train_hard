package me.kovp.trainhard.parameters_presentation.di

import me.kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import me.kovp.trainhard.parameters_domain.InsertNewExerciseInteractor
import me.kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import me.kovp.trainhard.parameters_domain.UpdateExistingExerciseInteractor
import me.kovp.trainhard.parameters_presentation.ParametersViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parametersModule = module {
    single { GetAllExercisesInteractor(exercisesApi = get()) }
    single { InsertNewExerciseInteractor(exercisesApi = get()) }
    single { UpdateExistingExerciseInteractor(exercisesApi = get()) }
    single { RemoveExerciseInteractor(exercisesApi = get()) }

    viewModel {
        ParametersViewModelImpl(
            getExercises = get(),
            insertExercise = get(),
            updateExercise = get(),
            removeExistingExercise = get(),
        )
    }
}
