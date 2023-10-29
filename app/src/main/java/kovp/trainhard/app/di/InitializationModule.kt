package kovp.trainhard.app.di

import kovp.trainhard.app.CurrentHostScreenFlowHolder
import kovp.trainhard.app.InitBaseExercisesInteractor
import kovp.trainhard.app.MainActivityViewModelImpl
import kovp.trainhard.app.navigation_graphs.RootNavigationGraphSpec
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val initializationModule = module {
    single {
        InitBaseExercisesInteractor(
            exercisesApi = get(),
        )
    }
    viewModel {
        MainActivityViewModelImpl(
            initBaseExercises = get(),
        )
    }
    single<CurrentHostScreenFlowHolder> { RootNavigationGraphSpec }
}
