package trainhard.kovp.core

import androidx.annotation.RawRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
    fun getConfig(@RawRes resId: Int): String
}
