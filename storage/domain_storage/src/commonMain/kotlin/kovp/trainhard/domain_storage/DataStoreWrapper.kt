package kovp.trainhard.domain_storage

interface DataStoreWrapper {
    suspend fun getLong(key: String): Long?
    suspend fun saveLong(key: String, value: Long?)
}
