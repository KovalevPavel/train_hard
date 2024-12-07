package kovp.trainhard.app

import android.content.Context
import trainhard.kovp.core.ResourceProvider

class ResourceProviderImpl(
    context: Context,
) : ResourceProvider {
    private val resources = context.resources

    override fun getString(resId: Int): String {
        return resources.getString(resId)
    }
}
