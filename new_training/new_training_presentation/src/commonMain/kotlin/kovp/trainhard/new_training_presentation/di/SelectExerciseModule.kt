package kovp.trainhard.new_training_presentation.di

import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectNewExerciseTypeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val selectExerciseModule = module {
    viewModel {
        SelectNewExerciseTypeViewModel(
            exercisesApi = get(),
            configHolder = get(),
        )
    }
}
