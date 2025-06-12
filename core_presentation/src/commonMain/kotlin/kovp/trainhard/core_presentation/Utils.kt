package kovp.trainhard.core_presentation

import androidx.navigation.NavController

fun <T> NavController.subscribeForResult(key: String, action: (T) -> Unit) {
    this.currentBackStackEntry
        ?.savedStateHandle
        ?.get<T>(key)
        ?.let {
            this.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
            action(it)
        }
}
