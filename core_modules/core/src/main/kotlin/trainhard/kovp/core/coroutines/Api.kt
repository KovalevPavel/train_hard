package trainhard.kovp.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
@Coroutine
fun <T> coroutine(
    dispatcher: Dispatcher = Dispatcher.Default,
    action: suspend Scope.() -> T,
): Async<T> {
    contract { callsInPlace(lambda = action, kind = InvocationKind.EXACTLY_ONCE) }

    return Async.newBuilder(
        deferred = CoroutineScope(dispatcher.coroutineDispatcher).async {
            runCatching { scope.action() }
        }
    )
}
