package kovp.trainhard.core_storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kovp.trainhard.domain_storage.DataStoreWrapper

internal class DataStoreWrapperImpl(
    private val preferences: DataStore<Preferences>,
) : DataStoreWrapper {
    override suspend fun getLong(key: String): Long? =
        preferences.data.first()[longPreferencesKey(key)]

    override suspend fun saveLong(key: String, value: Long?) {
        preferences.edit { prefs ->
            val k = longPreferencesKey(key)
            value?.let {
                prefs[k] = it
            }
                ?: prefs.remove(k)
        }
    }
}
