package me.kovp.trainhard.home_presentation.di

import me.kovp.trainhard.home_domain.GetCurrentDateInteractor
import me.kovp.trainhard.home_domain.GetGymCardHealthInteractor
import me.kovp.trainhard.home_presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { GetCurrentDateInteractor() }
    single { GetGymCardHealthInteractor() }
    viewModel {
        HomeViewModel(
            currentDate = get(),
            getGymCardHealth = get(),
        )
    }
}
