package me.kovp.trainhard.training_calendar_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.training_calendar_domain.GetTrainingDataInteractor
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TrainingCalendarViewModel(
    private val getTrainingData: GetTrainingDataInteractor,
) :
    BaseViewModel<TrainingCalendarState, TrainingCalendarEvent, TrainingCalendarAction>(
        initialState = TrainingCalendarState.Loading,
    ) {
    init {
        fetchData()
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

    private fun fetchData() {
        launch(
            action = {
                //TODO: добавить пагинацию и убрать хардкод
                val startDate = LocalDate.of(2022, 1, 1)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
                val currentDate = LocalDate.now()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                getTrainingData(startDate, currentDate)
                    .mapKeys { (timestamp, _) ->
                        val instant = Instant.ofEpochMilli(timestamp)
                        LocalDate.ofInstant(instant, ZoneId.systemDefault())
                    }
                    .let(TrainingCalendarState::Data)
                    .let(mutableStateFlow::update)
            },
            error = {
                Timber.e(it)
            }
        )
    }
}
