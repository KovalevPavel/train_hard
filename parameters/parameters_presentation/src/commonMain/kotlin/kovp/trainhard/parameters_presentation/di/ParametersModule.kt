package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.core.ResourceProvider
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import org.koin.dsl.module

val parametersModule = module {
    factory {
        val resProvider: ResourceProvider = get()
        ParametersViewModel(
            exercisesApi = get(),
            resourceProvider = resProvider,
            configHolder = get(),
        )
    }
}
