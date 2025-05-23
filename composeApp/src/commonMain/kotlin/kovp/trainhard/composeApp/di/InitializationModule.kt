package kovp.trainhard.composeApp.di

import kovp.trainhard.composeApp.InitBaseExercisesInteractor
import kovp.trainhard.composeApp.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val initializationModule = module {
    single { InitBaseExercisesInteractor(exercisesApi = get(), configHolder = get()) }

    viewModel {
        MainActivityViewModel(
            initBaseExercises = get(),
            exercisesApi = get(),
        )
    }
}
