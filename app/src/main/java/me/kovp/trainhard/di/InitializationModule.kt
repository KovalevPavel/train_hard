package me.kovp.trainhard.di

import me.kovp.trainhard.CurrentHostScreenFlowHolder
import me.kovp.trainhard.InitBaseExercisesInteractor
import me.kovp.trainhard.MainActivityViewModelImpl
import me.kovp.trainhard.navigation_graphs.RootNavigationGraphSpec
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
