package kovp.trainhard.home_presentation.home.presentation

import kotlinx.collections.immutable.persistentListOf
import kovp.trainhard.core_presentation.BaseViewModel
import kovp.trainhard.home_domain.EditGymCardHealthInteractor
import kovp.trainhard.home_domain.GetCurrentDateInteractor
import kovp.trainhard.home_domain.GetGymCardHealthInteractor
import kovp.trainhard.home_presentation.home.presentation.HomeScreenState.GymCardDates
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.NoProgramSelected
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.RestDay
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay.ExerciseCardVs

class HomeViewModel(
    private val currentDate: GetCurrentDateInteractor,
    private val getGymCardHealth: GetGymCardHealthInteractor,
    private val editGymCardHealth: EditGymCardHealthInteractor,
) : BaseViewModel<HomeScreenState, HomeAction, HomeEvent>(initialState = HomeScreenState.Loading) {

    private val mockPlanList = TrainingDay(
        items = persistentListOf(
            ExerciseCardVs(
                id = "dead_lift",
                title = "Становая тяга",
                sets = persistentListOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "спина, ноги"
            ),
            ExerciseCardVs(
                id = "squats",
                title = "Приседания",
                sets = persistentListOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "ноги"
            ),
            ExerciseCardVs(
                id = "bench_press",
                title = "Жим лежа",
                sets = persistentListOf(
                    "80кг : 3х6",
                    "80кг : 3х6",
                    "80кг : 3х6",
                ),
                muscleGroups = "грудь, плечи, трицепс"
            ),
        ),
    )

    @Suppress("unused")
    private val mockPlans = listOf(
        RestDay,
        NoProgramSelected,
        mockPlanList,
    )

    private var startDate: Long? = null
    private var endDate: Long? = null

    init {
        launch {
            getGymCardHealth()?.let { (start, end) ->
                startDate = start
                endDate = end
            }
            updateUi()
        }
    }

    override fun handleAction(action: HomeAction) {
        launch {
            when (action) {
                is HomeAction.OnStartTrainingClick -> {
                    emitEvent(event = HomeEvent.OpenNewTrainingScreen)
                }

                is HomeAction.OnGymCardPlateClick -> {
                    emitEvent(
                        event = HomeEvent.OpenDatePickerDialog(
                            startDate = startDate,
                            endDate = endDate,
                        ),
                    )
                }

                is HomeAction.EditGymCardDates -> {
                    startDate = action.startTimestamp
                    endDate = action.endTimestamp
                    editGymCardDates()
                }

                is HomeAction.OnCalendarClick -> {
                    emitEvent(event = HomeEvent.OpenTrainingCalendar)
                }
            }
        }
    }

    private fun updateUi() {
        HomeScreenState.Data(
            dateString = currentDate(),
            gymHealth = GymCardDates(startDate, endDate),
            todayPlan = NoProgramSelected,
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
