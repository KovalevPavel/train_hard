package kovp.trainhard.ui_theme.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import kovp.trainhard.ui_theme.TrainTypography

val themeTypography: TrainTypography @Composable get() = localTypographyProvider.current

internal val localTypographyProvider = compositionLocalOf<TrainTypography> {
    error("Can't find default factory fo TrainTypography")
}
