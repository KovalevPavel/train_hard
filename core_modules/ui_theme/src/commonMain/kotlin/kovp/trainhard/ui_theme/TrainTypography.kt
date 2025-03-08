package kovp.trainhard.ui_theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val textStyles: TrainTypography = TrainTypography(
    header1 = TextStyle(
        color = palette.white,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    header2 = TextStyle(
        color = palette.white,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    body1 = TextStyle(
        color = palette.white,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
    ),
    body2 = TextStyle(
        color = palette.white,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
    ),
    body3 = TextStyle(
        color = palette.white,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
    ),
)

@Immutable
data class TrainTypography(
    val header1: TextStyle,
    val header2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
)
