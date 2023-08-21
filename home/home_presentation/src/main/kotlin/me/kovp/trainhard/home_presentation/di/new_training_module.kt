package me.kovp.trainhard.home_presentation.di

import me.kovp.trainhard.home_domain.new_training.SaveTrainingInteractor
import me.kovp.trainhard.home_presentation.new_training.NewTrainingViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module

@OptIn(KoinReflectAPI::class)
val newTrainingModule = module {
    single { SaveTrainingInteractor() }
    viewModel<NewTrainingViewModelImpl>()
}
