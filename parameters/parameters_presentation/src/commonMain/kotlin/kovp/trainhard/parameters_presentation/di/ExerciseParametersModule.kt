package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersViewModel
import org.koin.dsl.module

fun exerciseParametersModule(arg: ExerciseParametersArg) = module {
     factory {
        ExerciseParametersViewModel(
            exerciseArgument = arg,
            exercisesApi = get<ExercisesApi>(),
            configHolder = get<ConfigHolder>(),
        )
    }
}
