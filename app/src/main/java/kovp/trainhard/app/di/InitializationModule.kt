package kovp.trainhard.app.di

import kovp.trainhard.app.InitBaseExercisesInteractor
import kovp.trainhard.app.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val initializationModule = module {
    single {
        InitBaseExercisesInteractor(
            exercisesApi = get(),
        )
    }
    viewModel {
        MainActivityViewModel(
            initBaseExercises = get(),
        )
    }
}
