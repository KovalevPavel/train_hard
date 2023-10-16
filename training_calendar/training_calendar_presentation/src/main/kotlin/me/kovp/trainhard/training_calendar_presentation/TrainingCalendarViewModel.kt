package me.kovp.trainhard.training_calendar_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.DATE_FORMAT_dd_MM_yyyy
import me.kovp.trainhard.core_domain.formatToDateString
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.training_calendar_domain.GetTrainingDataInteractor
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
        subscribeOnCalendarData()
    }

    override fun obtainEvent(event: TrainingCalendarEvent?) {
        viewModelScope.launch {
            when (event) {
                is TrainingCalendarEvent.OnTrainingDayClick -> {
                    event.day
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                        .formatToDateString(DATE_FORMAT_dd_MM_yyyy)
                        .let(TrainingCalendarAction::OpenNewTrainingScreen)
                }

                null -> {
                    TrainingCalendarAction.Empty
                }
            }
                .let { mutableActionFlow.emit(it) }
        }
    }

    private fun subscribeOnCalendarData() {
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
            .onEach {
                it.mapKeys { (timestamp, _) ->
                    val instant = Instant.ofEpochMilli(timestamp)
                    LocalDate.ofInstant(instant, ZoneId.systemDefault())
                }
                    .let(TrainingCalendarState::Data)
                    .let(mutableStateFlow::update)
            }
            .launchIn(viewModelScope)
    }
}
