package kovp.trainhard.ui_theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kovp.trainhard.ui_theme.providers.localColorsProvider
import kovp.trainhard.ui_theme.providers.localTypographyProvider

@Composable
fun TrainHardTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        localTypographyProvider provides textStyles,
        localColorsProvider provides palette,
    ) {
        content()
    }
}
