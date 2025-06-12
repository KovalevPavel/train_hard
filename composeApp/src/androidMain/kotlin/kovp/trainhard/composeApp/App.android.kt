package kovp.trainhard.composeApp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
actual fun SetStatusBar() {
    val backgroundColor = themeColors.black
    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = systemUiController) {
        systemUiController.setStatusBarColor(backgroundColor)

        onDispose { }
    }
}
