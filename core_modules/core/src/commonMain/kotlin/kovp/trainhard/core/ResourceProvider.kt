package kovp.trainhard.core

interface ResourceProvider {
    suspend fun getString(resId: Int): String
    suspend fun getConfig(resId: Int): String
}
