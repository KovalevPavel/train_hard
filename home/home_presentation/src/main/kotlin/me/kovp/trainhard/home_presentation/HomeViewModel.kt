package me.kovp.trainhard.home_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kovp.trainhard.core_domain.update
import me.kovp.trainhard.core_presentation.BaseViewModel
import me.kovp.trainhard.home_domain.EditGymCardHealthInteractor
import me.kovp.trainhard.home_domain.GetCurrentDateInteractor
import me.kovp.trainhard.home_domain.GetGymCardHealthInteractor
import me.kovp.trainhard.home_presentation.HomeScreenState.GymCardDates
import me.kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import me.kovp.trainhard.home_presentation.TodayPlan.RestDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay.Exercise

class HomeViewModel(
    private val currentDate: GetCurrentDateInteractor,
    private val getGymCardHealth: GetGymCardHealthInteractor,
    private val editGymCardHealt: EditGymCardHealthInteractor,
) : BaseViewModel<HomeScreenState, HomeEvent, HomeAction>(initialState = HomeScreenState.Loading) {

    private val mockPlanList = TrainingDay(
        items = listOf(
            Exercise(
                id = "dead_lift",
                title = "Становая тяга",
                sets = listOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "спина, ноги"
            ),
            Exercise(
                id = "squats",
                title = "Приседания",
                sets = listOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "ноги"
            ),
            Exercise(
                id = "bench_press",
                title = "Жим лежа",
                sets = listOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "грудь, плечи, трицепс"
            ),
        ),
    )

    private val mockPlans = listOf(
        RestDay,
        NoProgramSelected,
        mockPlanList,
    )

    private var startDate: Long? = null
    private var endDate: Long? = null

    init {
        launch(
            action = {
                getGymCardHealth()?.let { (start, end) ->
                    startDate = start
                    endDate = end
                }
                updateUi()
            }
        )
    }

    override fun obtainEvent(event: HomeEvent?) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.OnStartTrainingClick -> {
                    HomeAction.OpenNewTrainingScreen.let { mutableActionFlow.emit(it) }
                }

                is HomeEvent.OnGymCardPlateClick -> {
                    HomeAction.OpenDatePickerDialog(startDate = startDate, endDate = endDate)
                        .let { mutableActionFlow.emit(it) }
                }

                is HomeEvent.EditGymCardDates -> {
                    obtainEvent(null)
                    startDate = event.startTimestamp
                    endDate = event.endTimestamp
                    editGymCardDates()
                }

                is HomeEvent.OnCalendarClick -> {
                    HomeAction.OpenTrainingCalendar.let { mutableActionFlow.emit(it) }
                }

                null -> {
                    HomeAction.Empty.let { mutableActionFlow.emit(it) }
                }
            }
        }
    }

    private fun updateUi() {
        HomeScreenState.Data(
            dateString = currentDate(),
            gymHealth = GymCardDates(startDate, endDate),
            todayPlan = mockPlans.random(),
        )
            .let(mutableStateFlow::update)
    }

    private suspend fun editGymCardDates() {
        editGymCardHealt(
            startDate = startDate,
            endDate = endDate,
        )
        updateUi()
    }
}
