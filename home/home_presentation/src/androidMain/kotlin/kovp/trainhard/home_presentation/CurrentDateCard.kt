package kovp.trainhard.home_presentation

import androidx.compose.runtime.Composable
import kovp.trainhard.home_presentation.components.CurrentDateCard
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun CurrentDateCardPreview() {
    TrainHardTheme {
        CurrentDateCard(
            currentDate = "03\nсентября",
            currentProgramName = "",
            onDateClick = {},
        )
    }
}
