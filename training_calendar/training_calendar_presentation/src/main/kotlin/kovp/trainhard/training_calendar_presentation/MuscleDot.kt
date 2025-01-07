package kovp.trainhard.training_calendar_presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kovp.trainhard.components.dropShadow
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.core_domain.MuscleGroup.ABS
import kovp.trainhard.core_domain.MuscleGroup.ARMS
import kovp.trainhard.core_domain.MuscleGroup.BACK
import kovp.trainhard.core_domain.MuscleGroup.CHEST
import kovp.trainhard.core_domain.MuscleGroup.DELTOIDS
import kovp.trainhard.core_domain.MuscleGroup.LEGS
import kovp.trainhard.ui_theme.providers.themeColors

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

    Canvas(modifier = modifier) {
        drawCircle(color = argbColor)
    }
}
