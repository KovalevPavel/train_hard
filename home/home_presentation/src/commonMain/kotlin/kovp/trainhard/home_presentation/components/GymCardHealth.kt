package kovp.trainhard.home_presentation.components

import android.graphics.text.MeasuredText
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.dropShadow
import kovp.trainhard.home_presentation.R
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import kotlin.math.roundToInt

@Composable
fun GymCardHealth(
    modifier: Modifier = Modifier,
    cardHealth: Float?,
    onClick: () -> Unit,
) {
    when (cardHealth) {
        null -> {
            GymCardHealthStub(modifier = modifier, onStubClick = onClick)
        }

        else -> {
            GymCardHealthContent(
                modifier = modifier,
                cardHealth = cardHealth,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun GymCardHealthStub(
    modifier: Modifier,
    onStubClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = modifier
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
private fun GymCardHealthContent(
    modifier: Modifier,
    cardHealth: Float,
    onClick: () -> Unit,
) {
    val color = themeColors.lime
    val density = LocalDensity.current
    val radius = density.run { 8.dp.toPx() }

    Box(modifier.height(30.dp)) {
        BoxWithConstraints(Modifier.clickable(onClick = onClick).matchParentSize()) {
            val measurer = rememberTextMeasurer()
            val text = stringResource(id = R.string.gym_card)
            val healthWidth = maxWidth * cardHealth
            val textColor = themeColors.black

            var constraints = Constraints(
                maxWidth = density.run { healthWidth.toPx() }.roundToInt(),
                maxHeight = density.run { maxHeight.toPx() }.roundToInt(),
            )

            var measuredText = measurer.measure(
                constraints = constraints,
                text = text,
                style = themeTypography.body1,
                softWrap = false,
            )

            if (measuredText.hasVisualOverflow) {
                constraints = Constraints(
                    maxWidth = density.run { maxWidth.toPx() }.roundToInt(),
                    maxHeight = density.run { maxHeight.toPx() }.roundToInt(),
                )

                measuredText = measurer.measure(
                    constraints = constraints,
                    text = text,
                    style = themeTypography.body1,
                )
            }

            Canvas(modifier = Modifier.matchParentSize()) {
                canvasContent(
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    healthWidth = healthWidth,
                    color = color,
                    radius = radius,
                    textColor = textColor,
                    measuredText = measuredText,
                )
            }
        }
    }
}

private fun DrawScope.canvasContent(
    maxWidth: Dp,
    maxHeight: Dp,
    healthWidth: Dp,
    color: Color,
    radius: Float,
    textColor: Color,
    measuredText: TextLayoutResult,
) {
    val br = Brush.horizontalGradient(
        listOf(
            Color.Red,
            Color.Yellow,
            color,
        )
    )
    drawRoundRect(
        color = color,
        size = DpSize(maxWidth, maxHeight).toSize(),
        cornerRadius = CornerRadius(radius),
        alpha = .5f,
    )

    drawRoundRect(
        brush = br,
        size = DpSize(healthWidth, maxHeight).toSize(),
        cornerRadius = CornerRadius(radius)
    )

    val textWidth = density.run { measuredText.size.width.toDp() }

    val xOffset = when {
       textWidth > healthWidth -> {
            density.run { (maxWidth - textWidth).toPx() } / 2
        }

        else -> {
            density.run { (healthWidth - textWidth).toPx() } / 2
        }
    }

    val textOffset = Offset(
        x = xOffset,
        y = density.run { (maxHeight - measuredText.size.height.toDp()).toPx() } / 2,
    )

    drawText(measuredText, topLeft = textOffset, color = textColor)
}

@Preview(device = "spec:width=411dp,height=891dp")
@Preview(device = "id:Nexus S")
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
