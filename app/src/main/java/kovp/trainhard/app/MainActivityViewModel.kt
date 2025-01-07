package kovp.trainhard.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kovp.trainhard.core_domain.orFalse
import kovp.trainhard.parameters_core.GetAllExercisesInteractor

class MainActivityViewModel(
    private val initBaseExercises: InitBaseExercisesInteractor,
    private val getAllExercises: GetAllExercisesInteractor,
) : ViewModel() {
    var dbIsInitialized = false
        private set

    init {
        viewModelScope.launch {
            val exercisesAreEmpty = getAllExercises().firstOrNull()?.isNotEmpty().orFalse()
            delay(MIN_DELAY_MS)

            if (exercisesAreEmpty) {
                initBaseExercises()
            }

            dbIsInitialized = true
        }
    }

    companion object {
        private const val MIN_DELAY_MS = 1000L
    }
}
