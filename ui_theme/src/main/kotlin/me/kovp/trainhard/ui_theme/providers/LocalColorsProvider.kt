package me.kovp.trainhard.ui_theme.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import me.kovp.trainhard.ui_theme.TrainColors

internal val localColorsProvider = compositionLocalOf<TrainColors> {
    error("Can't find default factory fo TrainColors")
}

val themeColors: TrainColors @Composable get() = localColorsProvider.current
