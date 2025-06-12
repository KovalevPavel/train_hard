package kovp.trainhard.home_presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kovp.trainhard.home_presentation.components.GymCardHealth
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

//@Preview(device = "spec:width=411dp,height=891dp")
//@Preview(device = "id:Nexus S")
@Preview
@Composable
private fun GymCardHealthPreview(
    @PreviewParameter(GymCardHealthProvider::class) health: Float?,
) {
    TrainHardTheme {
        GymCardHealth(
            modifier = Modifier.fillMaxWidth(),
            cardHealth = health,
            onClick = {},
        )
    }
}

private class GymCardHealthProvider : PreviewParameterProvider<Float?> {
    override val values: Sequence<Float?>
        get() = sequenceOf(
            null,
            .1f,
            .2f,
            .5f,
            .9f,
            1f,
        )
}
