package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parametersModule = module {
    viewModel {
        ParametersViewModel(
            exercisesApi = get(),
            resourceProvider = get(),
            configHolder = get(),
        )
    }
}
