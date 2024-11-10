package kovp.trainhard.core_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State : Any, Action : Any, Event : Any>(
    initialState: State,
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow(initialState)

    val eventFlow: Flow<Event> by lazy { mutableEventFlow }

    protected val mutableEventFlow = MutableSharedFlow<Event>()

    abstract fun handleAction(action: Action)

    fun updateState(newState: State) {
        _state.value = newState
    }

    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        action: suspend () -> Unit,
        error: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch(context = context) {
            kotlin.runCatching { action() }
                .onFailure {
                    error(it)
                }
        }
    }
}
