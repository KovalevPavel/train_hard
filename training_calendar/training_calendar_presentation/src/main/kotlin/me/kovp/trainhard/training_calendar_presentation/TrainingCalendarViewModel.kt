package me.kovp.trainhard.training_calendar_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import timber.log.Timber
import java.time.LocalDate

class TrainingCalendarViewModel :
    BaseViewModel<TrainingCalendarState, TrainingCalendarEvent, TrainingCalendarAction>(
        initialState = TrainingCalendarState.Loading,
    ) {
    init {
        viewModelScope.launch {
            mapOf(
                LocalDate.of(2023, 10, 5) to listOf(MuscleGroup.ABS),
                LocalDate.of(2023, 10, 9) to listOf(MuscleGroup.CHEST, MuscleGroup.DELTOIDS),
                LocalDate.of(2023, 10, 11) to listOf(MuscleGroup.BACK, MuscleGroup.ABS),
                LocalDate.of(2023, 10, 13) to listOf(MuscleGroup.LEGS, MuscleGroup.ARMS),
                LocalDate.of(2023, 10, 15) to listOf(
                    MuscleGroup.CHEST,
                    MuscleGroup.DELTOIDS,
                    MuscleGroup.ARMS
                ),
            )
                .let(TrainingCalendarState::Data)
                .let(mutableStateFlow::update)
        }
    }

    override fun obtainEvent(event: TrainingCalendarEvent?) {
        viewModelScope.launch {
            when (event) {
                is TrainingCalendarEvent.OnTrainingDayClick -> {
                    Timber.e("day = ${event.day}")
                }

                null -> {
                    mutableActionFlow.emit(TrainingCalendarAction.Empty)
                }
            }
        }
    }
}
