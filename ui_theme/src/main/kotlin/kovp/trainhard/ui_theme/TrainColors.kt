package kovp.trainhard.ui_theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

internal val palette: TrainColors = TrainColors(
    black = Color(0xFF1C1C1E),
    gray = Color(0xFF505050),
    white = Color.White,
    lime = Color(0xFFD0FD3E),
    red = Color(0xFFFF2D55),
    blue = Color(0xFF2DB3FF),
    orange = Color(0xFFFF792D),
    yellow = Color(0xFFFFEA2D),
)

@Immutable
data class TrainColors(
    val black: Color,
    val gray: Color,
    val white: Color,
    val lime: Color,
    val red: Color,
    val blue: Color,
    val orange: Color,
    val yellow: Color,
)
