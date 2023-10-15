package me.kovp.trainhard.training_calendar_presentation.di

import me.kovp.trainhard.training_calendar_presentation.TrainingCalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trainingCalendarModule = module {
    viewModel { TrainingCalendarViewModel() }
}
