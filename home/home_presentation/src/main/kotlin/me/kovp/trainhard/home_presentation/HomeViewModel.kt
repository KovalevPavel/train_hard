package me.kovp.trainhard.home_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kovp.trainhard.home_domain.GetCurrentDateInteractor
import me.kovp.trainhard.home_domain.GetGymCardHealthInteractor
import me.kovp.trainhard.home_presentation.TodayPlan.NoProgramSelected
import me.kovp.trainhard.home_presentation.TodayPlan.RestDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay
import me.kovp.trainhard.home_presentation.TodayPlan.TrainingDay.Exercise

interface HomeViewModel {
    val dateStringFlow: Flow<Long>
    val gymHealthFlow: Flow<Float>
    val todayPlanFlow: Flow<TodayPlan>
}

class HomeViewModelImpl(
    private val currentDate: GetCurrentDateInteractor,
    private val getGymCardHealth: GetGymCardHealthInteractor,
) : ViewModel(), HomeViewModel {
    override val dateStringFlow = MutableStateFlow(0L)
    override val gymHealthFlow = MutableStateFlow(0f)
    override val todayPlanFlow = MutableStateFlow<TodayPlan>(NoProgramSelected)

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

    init {
        updateUi()
        loadTodayPlan()
    }

    private fun updateUi() {
        viewModelScope.launch {
            dateStringFlow.emit(
                value = currentDate(),
            )
            gymHealthFlow.emit(
                value = getGymCardHealth(),
            )
        }
    }

    private fun loadTodayPlan() {
        viewModelScope.launch {
            todayPlanFlow.emit(mockPlans.random())
        }
    }
}
