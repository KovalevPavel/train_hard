package kovp.trainhard.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kovp.trainhard.core_domain.orTrue
import kovp.trainhard.database_api.ExercisesApi

class MainActivityViewModel(
    private val initBaseExercises: InitBaseExercisesInteractor,
    private val exercisesApi: ExercisesApi,
) : ViewModel() {
    var dbIsInitialized = false
        private set

    init {
        viewModelScope.launch {
            val exercisesAreEmpty = exercisesApi.getExercises().firstOrNull()?.isEmpty().orTrue()
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
