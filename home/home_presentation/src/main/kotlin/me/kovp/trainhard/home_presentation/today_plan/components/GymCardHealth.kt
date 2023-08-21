package me.kovp.trainhard.home_presentation.today_plan.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun GymCardHealth(
    cardHealth: Float?,
) {
    cardHealth ?: return
    Box {
        Surface(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth(cardHealth),
            shape = RoundedCornerShape(8.dp),
            color = themeColors.lime,
        ) {
        }
        Surface(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = themeColors.lime.copy(alpha = 0.5f),
        ) {
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Абонемент",
            style = themeTypography.body1.copy(color = themeColors.black),
        )
    }
}