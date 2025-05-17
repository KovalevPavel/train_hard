package kovp.trainhard.core_storage

import android.content.SharedPreferences
import androidx.core.content.edit
import kovp.trainhard.domain_storage.SharedPreferencesWrapper

internal class SharedPreferencesWrapperImpl(
    private val preferences: SharedPreferences,
) : SharedPreferencesWrapper {
    override suspend fun getLong(key: String): Long? = preferences.all[key] as? Long

    override suspend fun saveLong(key: String, value: Long?) {
        preferences.edit {
            value?.let { putLong(key, value) }
                ?: remove(key)
        }
    }
}
