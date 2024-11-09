package kovp.trainhard.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val initBaseExercises: InitBaseExercisesInteractor,
) : ViewModel() {
    var dbIsInitialized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initBaseExercises()
            dbIsInitialized = true
        }
    }
}
