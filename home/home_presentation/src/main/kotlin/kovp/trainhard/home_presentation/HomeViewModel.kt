package kovp.trainhard.home_presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.home_domain.EditGymCardHealthInteractor
import kovp.trainhard.home_domain.GetCurrentDateInteractor
import kovp.trainhard.home_domain.GetGymCardHealthInteractor
import kovp.trainhard.home_presentation.HomeScreenState.GymCardDates
import kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import kovp.trainhard.home_presentation.TodayPlan.RestDay
import kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import kovp.trainhard.home_presentation.TodayPlan.TrainingDay.Exercise

class HomeViewModel(
    private val currentDate: GetCurrentDateInteractor,
    private val getGymCardHealth: GetGymCardHealthInteractor,
    private val editGymCardHealth: EditGymCardHealthInteractor,
) : BaseViewModel<HomeScreenState, HomeAction, HomeEvent>(initialState = HomeScreenState.Loading) {

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

    override fun handleAction(action: HomeAction) {
        viewModelScope.launch {
            when (action) {
                is HomeAction.OnStartTrainingClick -> {
                    HomeEvent.OpenNewTrainingScreen.let { mutableEventFlow.emit(it) }
                }

                is HomeAction.OnGymCardPlateClick -> {
                    HomeEvent.OpenDatePickerDialog(startDate = startDate, endDate = endDate)
                        .let { mutableEventFlow.emit(it) }
                }

                is HomeAction.EditGymCardDates -> {
                    startDate = action.startTimestamp
                    endDate = action.endTimestamp
                    editGymCardDates()
                }

                is HomeAction.OnCalendarClick -> {
                    HomeEvent.OpenTrainingCalendar.let { mutableEventFlow.emit(it) }
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
            .let(::updateState)
    }

    private suspend fun editGymCardDates() {
        editGymCardHealth(
            startDate = startDate,
            endDate = endDate,
        )
        updateUi()
    }
}
