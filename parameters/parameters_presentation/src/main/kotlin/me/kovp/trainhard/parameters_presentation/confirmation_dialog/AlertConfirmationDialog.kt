package me.kovp.trainhard.parameters_presentation.confirmation_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import me.kovp.trainhard.components.button.TrainButton
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import trainhard.core_dialogs.AlertConfirmationDialogScreen

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun AlertConfirmationDialog(
    arguments: AlertConfirmationDialogScreen,
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    Column(
        modifier = Modifier
            .background(
                color = themeColors.gray,
                shape = RoundedCornerShape(16.dp),
            )
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        with(arguments) {
            title.takeIf(String::isNotEmpty)?.let {
                Text(text = it, style = themeTypography.header1)
            }

            message.takeIf(String::isNotEmpty)?.let {
                Text(text = it, style = themeTypography.body1)
            }

            positiveAction.takeIf(String::isNotEmpty)?.let {
                TrainButton(
                    modifier = Modifier.align(Alignment.End),
                    label = it,
                    isPrimary = true,
                ) {
                    resultNavigator.navigateBack(result = true)
                }
            }

            negativeAction.takeIf(String::isNotEmpty)?.let {
                TrainButton(
                    modifier = Modifier.align(Alignment.End),
                    label = it,
                    isPrimary = false,
                ) {
                    resultNavigator.navigateBack(result = false)
                }
            }
        }
    }
}
