package me.kovp.trainhard.home_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kovp.home_presentation.R
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun GymCardHealth(
    startDate: Long?,
    endDate: Long?,
    onClick: () -> Unit,
) {
    val cardHealth = if (startDate == null || endDate == null) {
        null
    } else {
        val currentDays = System.currentTimeMillis().milliseconds.inWholeDays.toFloat()
        val startDays = startDate.milliseconds.inWholeDays
        val endDays = endDate.milliseconds.inWholeDays

        (endDays - currentDays) / (endDays - startDays)
    }

    cardHealth?.let {
        GymCardHealthContent(cardHealth = it) { onClick() }
    }
        ?: run {
            GymCardHealthStub { onClick() }
        }
}

@Composable
private fun GymCardHealthStub(
    onStubClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = onStubClick,
            color = themeColors.lime,
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(all = 8.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.gym_card_stub),
                style = themeTypography.body1.copy(color = themeColors.black),
            )
        }
    }
}

@Composable
fun GymCardHealthContent(
    cardHealth: Float,
    onClick: () -> Unit,
) {
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
            onClick = onClick,
            color = themeColors.lime.copy(alpha = 0.5f),
        ) {
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.gym_card),
            style = themeTypography.body1.copy(color = themeColors.black),
        )
    }
}
