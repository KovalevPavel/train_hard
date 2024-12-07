package kovp.trainhard.home_presentation.home.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.home_presentation.components.GymCardHealth
import kovp.trainhard.home_presentation.home.presentation.HomeAction
import kovp.trainhard.home_presentation.home.presentation.HomeScreenState
import kovp.trainhard.ui_theme.providers.themeColors
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    screenState: HomeScreenState.Data,
    handleAction: (HomeAction) -> Unit,
) {
    TopAppBar(
        title = {
            val startDate = screenState.gymHealth.start
            val endDate = screenState.gymHealth.end

            val cardHealth = if (startDate == null || endDate == null) {
                null
            } else {
                val currentDays = System.currentTimeMillis().milliseconds.inWholeDays.toFloat()
                val startDays = startDate.milliseconds.inWholeDays
                val endDays = endDate.milliseconds.inWholeDays

                val raw = (endDays - currentDays) / (endDays - startDays)
                raw.takeIf { it > 0f }?.coerceAtMost(1f)
            }

            GymCardHealth(
                modifier = Modifier.padding(end = 16.dp).fillMaxWidth(),
                cardHealth = cardHealth,
                onClick = {
                    handleAction(HomeAction.OnGymCardPlateClick)
                },
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black)
    )
}
