package me.kovp.trainhard.home_presentation.di

import me.kovp.trainhard.home_domain.today_plan.GetCurrentDateInteractor
import me.kovp.trainhard.home_domain.today_plan.GetGymCardHealthInteractor
import me.kovp.trainhard.home_presentation.HomeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { GetCurrentDateInteractor() }
    single { GetGymCardHealthInteractor() }
    viewModel {
        HomeViewModelImpl(
            currentDate = get(),
            getGymCardHealth = get(),
        )
    }
}
