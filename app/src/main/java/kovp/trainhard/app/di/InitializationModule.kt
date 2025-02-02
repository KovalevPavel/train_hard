package kovp.trainhard.app.di

import kovp.trainhard.app.InitBaseExercisesInteractor
import kovp.trainhard.app.MainActivityViewModel
import kovp.trainhard.parameters_core.GetAllExercisesInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val initializationModule = module {
    single { GetAllExercisesInteractor(exercisesApi = get()) }
    single { InitBaseExercisesInteractor(exercisesApi = get(), configHolder = get()) }

    viewModel {
        MainActivityViewModel(
            initBaseExercises = get(),
            getAllExercises = get(),
        )
    }
}
