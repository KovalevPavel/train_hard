package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun exerciseParametersModule(arg: ExerciseParametersArg) = module {
    viewModel {
        ExerciseParametersViewModel(
            exerciseArgument = arg,
            exercisesApi = get(),
            resourceProvider = get(),
            configHolder = get(),
        )
    }
}
