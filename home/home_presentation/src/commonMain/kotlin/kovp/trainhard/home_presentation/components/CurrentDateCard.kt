package kovp.trainhard.home_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.jetbrains.compose.resources.stringResource
import trainhard.home_presentation.generated.resources.Res
import trainhard.home_presentation.generated.resources.active_program_title
import trainhard.home_presentation.generated.resources.default_program

@Composable
fun CurrentDateCard(
    modifier: Modifier = Modifier,
    currentDate: String?,
    currentProgramName: String,
    onDateClick: () -> Unit,
) {
    currentDate ?: return
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = themeColors.gray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CalendarCard(date = currentDate, onClick = onDateClick)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.CenterVertically,
                )
            ) {
                Text(
                    text = stringResource(resource = Res.string.active_program_title),
                    style = themeTypography.body1,
                )
                Text(
                    text = currentProgramName.takeIf(String::isNotEmpty)
                        ?: stringResource(resource = Res.string.default_program),
                    style = themeTypography.header1
                        .takeIf { currentProgramName.isNotEmpty() }
                        ?: themeTypography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
