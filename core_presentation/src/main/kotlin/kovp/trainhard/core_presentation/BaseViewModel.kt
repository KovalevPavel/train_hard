package kovp.trainhard.core_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import trainhard.kovp.core.coroutines.Dispatcher
import trainhard.kovp.core.coroutines.Scope
import trainhard.kovp.core.coroutines.coroutine

abstract class BaseViewModel<State : Any, Action : Any, Event : Any>(
    initialState: State,
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow(initialState)

    val eventFlow: Flow<Event> by lazy { _channel.receiveAsFlow() }

    private val _channel = Channel<Event>()

    abstract fun handleAction(action: Action)

    protected fun updateState(newState: State) {
        _state.value = newState
    }

    protected suspend fun emitEvent(event: Event) {
        _channel.send(event)
    }

    protected fun launch(
        dispatcher: Dispatcher = Dispatcher.Default,
        error: (Throwable) -> Unit = {},
        action: suspend Scope.() -> Unit,
    ): Job {
        return viewModelScope.launch {
            coroutine(dispatcher, action).onFailure(error).await()
        }
    }
}
