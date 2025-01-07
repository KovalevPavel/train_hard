package kovp.trainhard.training_calendar_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kovp.trainhard.core_domain.toStartOfDay
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.training_calendar_domain.GetTrainingDataInteractor
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TrainingCalendarViewModel(
    private val getTrainingData: GetTrainingDataInteractor,
) :
    BaseViewModel<TrainingCalendarState, TrainingCalendarAction, TrainingCalendarEvent>(
        initialState = TrainingCalendarState.Loading,
    ) {
    init {
        subscribeOnCalendarData()
    }

    override fun handleAction(action: TrainingCalendarAction) {
        launch {
            when (action) {
                is TrainingCalendarAction.OnTrainingDayClick -> {
                    action.day
                        .toStartOfDay()
                        .let(TrainingCalendarEvent::OpenNewTrainingScreen)
                }
            }
                .let { emitEvent(it) }
        }
    }

    private fun subscribeOnCalendarData() {
        //TODO: добавить пагинацию и убрать хардкод
        val startDate = LocalDate.of(HARDCODED_START_YEAR, 1, 1).toStartOfDay()
        val currentDate = LocalDate.now().toStartOfDay()

        getTrainingData(startDate, currentDate)
            .onEach {
                delay(STATE_UPDATE_DELAY_MS)
                it.mapKeys { (timestamp, _) ->
                    val instant = Instant.ofEpochMilli(timestamp)
                    LocalDate.ofInstant(instant, ZoneId.systemDefault())
                }
                    .let(TrainingCalendarState::Data)
                    .let (::updateState)
            }
            .launchIn(viewModelScope)
    }

    companion object {
        /**
         * Устраняет микрофриз перед показом календаря
         */
        private const val STATE_UPDATE_DELAY_MS = 500L
        private const val HARDCODED_START_YEAR = 2022
    }
}
