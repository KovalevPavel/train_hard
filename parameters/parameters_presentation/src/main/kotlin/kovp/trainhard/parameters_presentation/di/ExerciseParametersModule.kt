package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.parameters_presentation.exercise_parameters.ExerciseParametersArg
import kovp.trainhard.parameters_presentation.exercise_parameters.ExerciseParametersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun exerciseParametersModule(arg: ExerciseParametersArg) = module {
    viewModel {
        ExerciseParametersViewModel(
            exerciseArgument = arg,
            insertExercise = get(),
            updateExercise = get(),
            resourceProvider = get(),
        )
    }
}
