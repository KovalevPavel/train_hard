package kovp.trainhard.components.selectors

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackNavigationHandler(onDismiss: () -> Unit) {
    BackHandler { onDismiss() }
}
