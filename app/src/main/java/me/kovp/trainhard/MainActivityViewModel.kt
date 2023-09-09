package me.kovp.trainhard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface MainActivityViewModel {
    val dbInitializationState: Flow<Boolean>
}

class MainActivityViewModelImpl(
    private val initBaseExercises: InitBaseExercisesInteractor,
) : ViewModel(), MainActivityViewModel {
    override val dbInitializationState = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initBaseExercises()
        }
    }
}
