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

abstract class BaseViewModel<State : Any, Event : Any, Action : Any>(
    initialState: State,
) : ViewModel() {
    val stateFlow: StateFlow<State> by lazy { mutableStateFlow }
    val actionFlow: Flow<Action> by lazy { mutableActionFlow }
    protected val mutableStateFlow = MutableStateFlow(initialState)
    protected val mutableActionFlow = MutableSharedFlow<Action>()

    abstract fun obtainEvent(event: Event?)

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
