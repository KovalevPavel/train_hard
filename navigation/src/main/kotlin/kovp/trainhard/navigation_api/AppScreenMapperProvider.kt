package kovp.trainhard.navigation_api

import androidx.compose.runtime.compositionLocalOf

val localScreenMapper = compositionLocalOf<AppScreenMapper> {
    error("No default implementation")
}
