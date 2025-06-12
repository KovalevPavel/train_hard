package kovp.trainhard.components.counter

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class ButtonType(
    val topStart: Dp,
    val topEnd: Dp,
    val bottomEnd: Dp,
    val bottomStart: Dp,
) {
    data object Add : ButtonType(
        topStart = 0.dp,
        topEnd = 20.dp,
        bottomEnd = 20.dp,
        bottomStart = 0.dp,
    )

    data object Remove : ButtonType(
        topStart = 20.dp,
        topEnd = 0.dp,
        bottomEnd = 0.dp,
        bottomStart = 20.dp,
    )
}
