package trainhard.kovp.core.coroutines

import kotlinx.coroutines.Dispatchers

sealed interface Dispatcher {
    data object Main: Dispatcher
    data object Io: Dispatcher
    data object Default: Dispatcher
}

internal val Dispatcher.coroutineDispatcher
    get() = when (this) {
        is Dispatcher.Default -> Dispatchers.Default
        is Dispatcher.Io -> Dispatchers.IO
        is Dispatcher.Main -> Dispatchers.Main
    }
