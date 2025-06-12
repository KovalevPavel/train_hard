package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import org.koin.dsl.module

val parametersModule = module {
    factory {
        ParametersViewModel(
            exercisesApi = get(),
            configHolder = get(),
        )
    }
}
