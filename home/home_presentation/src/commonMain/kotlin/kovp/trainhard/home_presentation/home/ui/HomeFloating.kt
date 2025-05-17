package kovp.trainhard.home_presentation.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kovp.trainhard.home_presentation.R
import kovp.trainhard.home_presentation.home.presentation.HomeAction
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun HomeFloating(
    handleAction: (HomeAction) -> Unit,
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        containerColor = themeColors.lime,
        shape = RoundedCornerShape(size = 100.dp),
        onClick = {
            handleAction(HomeAction.OnStartTrainingClick)
        },
    ) {
        Text(
            text = stringResource(id = R.string.start_training_fab),
            style = themeTypography.body1.copy(
                fontSize = 14.sp,
                color = themeColors.black,
            ),
        )
    }
}
