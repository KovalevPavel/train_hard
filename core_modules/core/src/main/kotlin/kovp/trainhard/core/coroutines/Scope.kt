package kovp.trainhard.core.coroutines

import kotlinx.coroutines.CoroutineScope

class Scope internal constructor(val coroutineScope: CoroutineScope)

internal val CoroutineScope.scope get() = Scope(coroutineScope = this)
