package kovp.trainhard.core_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State : Any, Action : Any, Event : Any>(
    initialState: State,
) : ViewModel() {
    var state: State = initialState
        protected set

    val actionFlow: Flow<Event> by lazy { mutableActionFlow }

    protected val mutableActionFlow = MutableSharedFlow<Event>()

    abstract fun handleAction(event: Action?)

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
