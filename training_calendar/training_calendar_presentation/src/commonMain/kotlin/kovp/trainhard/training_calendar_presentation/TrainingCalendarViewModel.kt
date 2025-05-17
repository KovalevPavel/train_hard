package kovp.trainhard.training_calendar_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.core_domain.toStartOfDay
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.training_calendar_domain.GetTrainingDataInteractor
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class TrainingCalendarViewModel(
    private val getTrainingData: GetTrainingDataInteractor,
    private val configHolder: ConfigHolder,
) :
    BaseViewModel<TrainingCalendarState, TrainingCalendarAction, TrainingCalendarEvent>(
        initialState = TrainingCalendarState.Loading,
    ) {
    val firstMonthsOffset = configHolder.trainingConfig.firstMonthOffset.toLong()

    init {
        subscribeOnCalendarData()
    }

    override fun handleAction(action: TrainingCalendarAction) {
        launch {
            when (action) {
                is TrainingCalendarAction.OnTrainingDayClick -> {
                    action.day
//                        .toKotlinLocalDate()
                        .toStartOfDay()
                        .let(TrainingCalendarEvent::OpenNewTrainingScreen)
                }
            }
                .let { emitEvent(it) }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun subscribeOnCalendarData() {
        //TODO: добавить пагинацию
        val startDate = kotlinx.datetime.LocalDate(
            year = configHolder.trainingConfig.startYear,
            monthNumber = 1,
            dayOfMonth = 1,
        )
            .toStartOfDay()
        val currentDate = Clock.System.now().toStartOfDay()

        getTrainingData(startDate, currentDate)
            .onEach {
                delay(STATE_UPDATE_DELAY_MS)
                it.mapKeys { (timestamp, _) ->
//                    val instant = Instant.ofEpochMilli(timestamp)
                    val daysEpoch = Duration.convert(timestamp.toDouble(), DurationUnit.MILLISECONDS, DurationUnit.DAYS)
                    LocalDate.fromEpochDays(daysEpoch.toInt())
                }
                    .let { trainings ->
                        TrainingCalendarState.Data(
                            muscleGroups = configHolder.exercisesConfig.muscleGroups.map { (k, v) ->
                                TrainingCalendarState.LegendMuscleGroupVs(
                                    group = k,
                                    title = v,
                                )
                            }
                                .toImmutableList(),
                            trainings = trainings.toImmutableMap(),
                        )
                    }
                    .let(::updateState)
            }
            .launchIn(viewModelScope)
    }

    companion object {
        /**
         * Устраняет микрофриз перед показом календаря
         */
        private const val STATE_UPDATE_DELAY_MS = 500L
    }
}
