package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.new_trainig_domain.SaveTrainingInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newTrainingModule = module {
    single { SaveTrainingInteractor() }
    viewModel {
        NewTrainingViewModelImpl(
            saveTraining = get(),
        )
    }
}
