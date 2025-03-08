package kovp.trainhard.app

import android.content.Context
import kovp.trainhard.core.ResourceProvider

class ResourceProviderImpl(
    context: Context,
) : ResourceProvider {
    private val resources = context.resources

    override suspend fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    override suspend fun getConfig(resId: Int): String {
        return resources.openRawResource(resId).bufferedReader().use { it.readText() }
    }
}
