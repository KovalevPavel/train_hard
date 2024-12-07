package kovp.trainhard.parameters_presentation.di

import kovp.trainhard.parameters_domain.GetAllExercisesInteractor
import kovp.trainhard.parameters_domain.InsertNewExerciseInteractor
import kovp.trainhard.parameters_domain.RemoveExerciseInteractor
import kovp.trainhard.parameters_domain.UpdateExistingExerciseInteractor
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parametersModule = module {
    single { GetAllExercisesInteractor(exercisesApi = get()) }
    single { InsertNewExerciseInteractor(exercisesApi = get()) }
    single { UpdateExistingExerciseInteractor(exercisesApi = get()) }
    single { RemoveExerciseInteractor(exercisesApi = get()) }

    viewModel {
        ParametersViewModel(
            getExercises = get(),
            removeExistingExercise = get(),
            resourceProvider = get(),
        )
    }
}
