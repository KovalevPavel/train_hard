package me.kovp.trainhard.core_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State : Any>(
    initialState: State,
) : ViewModel() {
    val stateFlow: StateFlow<State> by lazy { mutableStateFlow }
    protected val mutableStateFlow = MutableStateFlow(initialState)

    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        action: suspend () -> Unit,
        error: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch(context = context) {
            kotlin.runCatching { action() }
                .onFailure {
                    error(it)
                    mutableStateFlow.value
                }
        }
    }
}
