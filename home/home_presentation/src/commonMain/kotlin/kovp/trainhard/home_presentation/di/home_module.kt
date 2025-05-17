package kovp.trainhard.home_presentation.di

import kovp.trainhard.domain_storage.GymCardQualifier
import kovp.trainhard.home_domain.EditGymCardHealthInteractor
import kovp.trainhard.home_domain.GetCurrentDateInteractor
import kovp.trainhard.home_domain.GetGymCardHealthInteractor
import kovp.trainhard.home_presentation.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { GetCurrentDateInteractor() }
    single { EditGymCardHealthInteractor(get(qualifier = GymCardQualifier)) }

    single { GetGymCardHealthInteractor(prefs = get(qualifier = GymCardQualifier)) }
    viewModel {
        HomeViewModel(
            currentDate = get(),
            getGymCardHealth = get(),
            editGymCardHealth = get(),
        )
    }
}
