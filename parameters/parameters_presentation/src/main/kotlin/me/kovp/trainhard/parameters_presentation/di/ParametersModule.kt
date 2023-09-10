package me.kovp.trainhard.parameters_presentation.di

import me.kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import me.kovp.trainhard.parameters_presentation.ParametersViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parametersModule = module {
    single { GetAllExercisesInteractor(exercisesApi = get()) }

    viewModel { ParametersViewModelImpl(getExercises = get()) }
}
