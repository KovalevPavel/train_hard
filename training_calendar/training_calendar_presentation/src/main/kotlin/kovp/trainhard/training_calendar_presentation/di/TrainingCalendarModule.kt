package kovp.trainhard.training_calendar_presentation.di

import kovp.trainhard.training_calendar_domain.GetTrainingDataInteractor
import kovp.trainhard.training_calendar_presentation.TrainingCalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trainingCalendarModule = module {
    single {
        GetTrainingDataInteractor(
            calendarApi = get(),
        )
    }
    viewModel {
        TrainingCalendarViewModel(
            getTrainingData = get(),
            configHolder = get(),
        )
    }
}
