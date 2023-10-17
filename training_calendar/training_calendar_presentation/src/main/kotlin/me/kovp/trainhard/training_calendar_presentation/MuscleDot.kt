package me.kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.MuscleGroup.ABS
import me.kovp.trainhard.core_domain.MuscleGroup.ARMS
import me.kovp.trainhard.core_domain.MuscleGroup.BACK
import me.kovp.trainhard.core_domain.MuscleGroup.CHEST
import me.kovp.trainhard.core_domain.MuscleGroup.DELTOIDS
import me.kovp.trainhard.core_domain.MuscleGroup.LEGS
import me.kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun MuscleDot(
    modifier: Modifier = Modifier,
    muscleGroup: MuscleGroup,
) {
    val argbColor = when (muscleGroup) {
        ARMS -> themeColors.yellow
        CHEST -> themeColors.red
        BACK -> themeColors.blue
        LEGS -> themeColors.lime
        DELTOIDS -> themeColors.orange
        ABS -> themeColors.white
    }
        .toArgb()

    Box(
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            drawRoundRect(
                color = Color(argbColor),
                size = size,
                cornerRadius = CornerRadius(size.height / 2, size.width / 2),
            )
        }
    }
}
