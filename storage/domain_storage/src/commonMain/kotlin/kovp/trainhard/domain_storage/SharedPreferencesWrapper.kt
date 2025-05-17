package kovp.trainhard.domain_storage

interface SharedPreferencesWrapper {
    suspend fun getLong(key: String): Long?
    suspend fun saveLong(key: String, value: Long?)
}
