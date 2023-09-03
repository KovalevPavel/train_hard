package me.kovp.trainhard.new_training_presentation.di

import me.kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectNewExerciseTypeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val selectExerciseModule = module {
    viewModel {
        SelectNewExerciseTypeViewModelImpl(
            exercisesApi = get(),
        )
    }
}
