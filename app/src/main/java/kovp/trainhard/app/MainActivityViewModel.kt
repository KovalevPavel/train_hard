package kovp.trainhard.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import trainhard.kovp.core.coroutines.coroutine

class MainActivityViewModel(
    private val initBaseExercises: InitBaseExercisesInteractor,
) : ViewModel() {
    var dbIsInitialized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            coroutine {
                initBaseExercises()
                dbIsInitialized = true
            }
                .await()
        }
    }
}
