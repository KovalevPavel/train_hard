package trainhard.kovp.core.coroutines

import kotlinx.coroutines.Deferred

@Suppress("unused")
class Async<T> private constructor(private val deferred: Deferred<Result<T>>) {
    private var onFailureBlock: (Throwable) -> Unit = {}
    private var onSuccessBlock: (T) -> Unit = {}

    suspend fun await(): Result<T> = deferred.await()
        .onFailure(onFailureBlock)
        .onSuccess(onSuccessBlock)

    fun onFailure(action: (Throwable) -> Unit): Async<T> {
        onFailureBlock = action
        return this
    }

    fun onSuccess(action: (T) -> Unit): Async<T> {
        onSuccessBlock = action
        return this
    }

    companion object {
        fun <T>newBuilder(deferred: Deferred<Result<T>>) = Async(deferred)
    }
}
