package kovp.trainhard.home_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kovp.trainhard.home_presentation.R
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun CurrentDateCard(
    currentDate: String?,
    currentProgramName: String,
    onDateClick: () -> Unit,
) {
    currentDate ?: return
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = themeColors.gray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Surface(
                modifier = Modifier
                    .size(110.dp),
                onClick = onDateClick,
                shape = RoundedCornerShape(16.dp),
                color = themeColors.lime,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Top,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val (number, month) = currentDate.split("\n")
                    Text(
                        text = number,
                        style = themeTypography.header1.copy(
                            color = themeColors.black,
                            fontSize = 36.sp,
                        )
                    )
                    Text(
                        text = month,
                        style = themeTypography.header1.copy(
                            color = themeColors.black,
                            fontSize = 20.sp,
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.CenterVertically,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.active_program_title),
                    style = themeTypography.body1,
                )
                Text(
                    text = currentProgramName.takeIf(String::isNotEmpty)
                        ?: stringResource(id = R.string.default_program),
                    style = themeTypography.header1
                        .takeIf { currentProgramName.isNotEmpty() }
                        ?: themeTypography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
